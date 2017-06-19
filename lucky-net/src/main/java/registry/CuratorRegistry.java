package registry;

import com.alibaba.fastjson.JSON;
import com.netflix.curator.framework.api.CuratorWatcher;
import common.GlobalConstants;
import lombok.Getter;
import lombok.Setter;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import lucky.util.zk.CuratorClient;
import lucky.util.zk.CuratorFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import rpc.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @Author:chaoqiang.zhou
 * @Description:注册中心有点太乱
 * @Date:Create in 13:32 2017/6/19
 */
public class CuratorRegistry implements Registry {

    private static final Logger logger = LoggerFactory.getLogger(CuratorRegistry.class);
    public static final String zkAddress;
    public final String ONLINE_NODE = "online_node";
    public final String OFFLINE_NODE = "off_node";
    public static final Registry registry = new CuratorRegistry();


    //启动得时候添加watcher机制
    static {
        //todo：从这里进行zkaddress的初始化操作
        zkAddress = "";
    }

    public static final CuratorClient client = CuratorFactory.get(GlobalConstants.NAME_SPACE, zkAddress);

    @Override
    public void register(Supplier<Provider> supplier) throws Exception {
        Provider provider = supplier.get();
        String path = createOnlinePath(provider);
        boolean isPathExists = client.isPathExists(path);
        String obj = JSON.toJSONString(provider);
        //如果存在了就更新该节点得信息
        if (isPathExists) {
            //更新改节点的信息操作
            client.writePath(path, obj);
            return;
        }
        //注册节点操作,持久化节点，除非手动进行删除的操作
        client.createPath(path, CreateMode.PERSISTENT, obj);
    }

    @Override
    public void remove(Provider provider) {

    }


    //获取服务节点列表信息
    @Override
    public List<Provider> lookup(String serverName, BiConsumer<String, List<Provider>> consumer) {
        List<Provider> list = new ArrayList<>();
        try {
            //服务下面的上线节点信息
            String onlinePath = "/" + serverName + "/" + ONLINE_NODE;
            List<String> childPath = client.getChildren(onlinePath);
            if (childPath == null || childPath.isEmpty()) {
                return list;
            }
            //遍历每一个节点信息，获取provider信息
            for (String nodePath : childPath) {
                String dataPath = onlinePath + "/" + nodePath;
                String data = client.readPath(dataPath);
                Provider provider = JSON.parseObject(data, Provider.class);
                list.add(provider);
            }
            if (consumer != null) {
                //添加一个watcher的机制
                String watcherPath = "/" + GlobalConstants.NAME_SPACE + "/" + serverName;
                client.watcherPath(watcherPath, new nodeWatcher(consumer, serverName));
            }
        } catch (Exception e) {
            logger.error("从配置中心获取服务列表出错，服务名称{},错误信息{}", serverName, e);
            return list;
        }
        return list;
    }

    @Override
    public void add(Provider provider) {

    }

    @Override
    public void watcher(String path) {

    }


    private String createOnlinePath(Provider provider) {
        return "/" + provider.getName() + "/" + ONLINE_NODE + "/" + provider.getAddress() + ":" + provider.getPort();
    }


    static class nodeWatcher implements CuratorWatcher {


        private String serverName;
        @Getter
        @Setter
        private BiConsumer<String, List<Provider>> consumer;

        public nodeWatcher(BiConsumer<String, List<Provider>> consumer, String serverName) {
            this.consumer = consumer;
            this.serverName = serverName;
        }

        @Override
        public void process(WatchedEvent watchedEvent) throws Exception {
            if (watchedEvent.getType() == Watcher.Event.EventType.NodeDeleted) {
                List<String> childNodes = client.getChildren(watchedEvent.getPath());
                List<Provider> providers = new ArrayList<>();
                if (childNodes != null || !childNodes.isEmpty()) {
                    for (String node : childNodes) {
                        String nodeData = client.readPath(node);
                        providers.add(JSON.parseObject(nodeData, Provider.class));
                    }
                }
                logger.info("节点发生变更，服务{}，更新列表信息", serverName);
                //下线得节点在web控制中心进行控制
                consumer.accept(serverName, providers);
                //节点下线
            } else if (watchedEvent.getType() == Watcher.Event.EventType.NodeCreated) {
                //节点上线
                List<String> childNodes = client.getChildren(watchedEvent.getPath());
                List<Provider> providers = new ArrayList<>();
                if (childNodes != null || !childNodes.isEmpty()) {
                    for (String node : childNodes) {
                        String nodeData = client.readPath(node);
                        providers.add(JSON.parseObject(nodeData, Provider.class));
                    }
                }
                logger.info("节点发生变更，服务{}，更新列表信息", serverName);
                //下线得节点在web控制中心进行控制
                consumer.accept(serverName, providers);
            }
        }
    }
}
