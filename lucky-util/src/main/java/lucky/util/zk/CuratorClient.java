package lucky.util.zk;

import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.framework.api.CuratorWatcher;
import com.netflix.curator.framework.state.ConnectionState;
import com.netflix.curator.framework.state.ConnectionStateListener;
import com.netflix.curator.retry.RetryNTimes;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author:chaoqiang.zhou
 * @Description:zk的client端
 * @Date:Create in 13:08 2017/6/19
 */
public class CuratorClient implements Closeable {
    private String zkAddress;
    private String nameSpace;
    private CuratorFramework curator;
    private static final Logger logger = LoggerFactory.getLogger(CuratorClient.class);
    private final int TIME_OUT = 5000;
    //启动得信号变量
    private CountDownLatch connectedSemaphore = new CountDownLatch(1);


    public CuratorClient(String nameSpace, String zkAddress) {
        this.nameSpace = nameSpace;
        this.zkAddress = zkAddress;
        init(nameSpace, zkAddress);
    }

    public void init(String nameSpace, String address) {
        try {
            this.nameSpace = nameSpace;
            this.zkAddress = address;
            this.curator = CuratorFrameworkFactory.builder().connectString(this.zkAddress)
                    .namespace(this.nameSpace)
                    .retryPolicy(new RetryNTimes(5, 1000))
                    .connectionTimeoutMs(TIME_OUT).build();
            this.curator.getConnectionStateListenable().addListener(new ConnectionStateListener() {
                @Override
                public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                    logger.info("Zookeeper connection state changed {}.", connectionState);
                    if (connectionState == ConnectionState.LOST) {
                        logger.info("Zookeeper connection lost,retry again");
                        retry();
                    } else if (connectionState == ConnectionState.CONNECTED) {
                        logger.info("Zookeeper connection has benn established");
                        connectedSemaphore.countDown();
                    } else if (connectionState == ConnectionState.RECONNECTED) {
                        logger.info("Zookeeper connection has been re-established, will re-subscribe and re-register.");
                    }
                }
            });

            this.curator.start();
            connectedSemaphore.await();
        } catch (Exception e) {
            logger.info("create zookeeper failed,error{}", e);
        }

    }


    public void removeListener(ConnectionStateListener stateListener) {
        this.curator.getConnectionStateListenable().removeListener(stateListener);
    }


    //retry connect 操作
    public void retry() {
        try {
            //重连接前，先进行关闭之前得连接操作
            close();
            init(this.nameSpace, this.zkAddress);
        } catch (Exception e) {
            logger.info("zk retry failed,error{}", e);
        }

    }

    @Override
    public void close() throws IOException {

        try {
            this.curator.close();
            this.curator = null;

        } catch (Exception e) {
            throw e;
        }
    }


    public Stat getStat(String path) throws Exception {
        return (Stat) this.curator.checkExists().forPath(path);
    }

    public boolean isPathExists(String path) throws Exception {
        Stat serverStat = getStat(path);
        if (serverStat == null) {
            return false;
        }

        return true;

    }


    public void createPath(String path, CreateMode mode) throws Exception {
        createPath(path, mode, "");
    }


    public void createPath(String path, CreateMode mode, String value)
            throws Exception {

        this.curator.create().creatingParentsIfNeeded().withMode(mode).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(path, value.getBytes(Charset.forName("utf-8")));
    }

    public void deletePath(String path)
            throws Exception {
        this.curator.delete().forPath(path);
    }

    public void writePath(String path, String value)
            throws Exception {
        this.curator.setData().forPath(path, value.getBytes(Charset.forName("utf-8")));
    }


    public String readPath(String path)
            throws Exception {
        byte[] buffer = this.curator.getData().forPath(path);
        return new String(buffer);
    }

    public String watcherPath(String path, CuratorWatcher watcher)
            throws Exception {
        byte[] buffer = this.curator.getData().usingWatcher(watcher).forPath(path);
        return new String(buffer);
    }

    public List<String> getChildren(String path)
            throws Exception {
        return this.curator.getChildren().forPath(path);
    }

    public CuratorFramework getCurator() {
        return this.curator;
    }


    static class StateListener implements ConnectionStateListener {

        @Override
        public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
            logger.info("Zookeeper connection state changed {}.", connectionState);
            if (connectionState == ConnectionState.LOST) {
                logger.info("Zookeeper connection lost,retry again");
            } else if (connectionState == ConnectionState.CONNECTED) {
                logger.info("Zookeeper connection has benn established");
            } else if (connectionState == ConnectionState.RECONNECTED) {
                logger.info("Zookeeper connection has been re-established, will re-subscribe and re-register.");
            }
        }
    }
}
