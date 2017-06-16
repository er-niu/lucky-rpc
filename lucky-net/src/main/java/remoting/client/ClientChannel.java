package remoting.client;

import remoting.data.NettyResponse;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:chaoqiang.zhou
 * @Description:输出数据的channel 参考：http://blog.csdn.net/ghsau/article/details/7481142
 * @Date:Create in 15:30 2017/6/15
 */
public class ClientChannel extends NioSocketChannel {

    private NettyResponse respons;
    private ReentrantLock lock = new ReentrantLock();
    private Condition hasMsg = lock.newCondition();


    public void reset() {
        respons = null;
    }

    public NettyResponse get(long timeOut) throws InterruptedException {
        lock.lock();
        try {
            long end = System.currentTimeMillis() + timeOut;
            long time = timeOut;
            while (respons == null) {
                boolean ok = hasMsg.await(timeOut, TimeUnit.MILLISECONDS);
                //超时时间到了
                if (ok || (end - System.currentTimeMillis()) <= 0) {
                    break;
                }
            }
        } finally {
            //一定要手动释放掉锁
            lock.unlock();
        }

        return respons;
    }


    public void set(NettyResponse message) {
        lock.lock();
        try {
            respons = message;
            //信号量，释放上述get操作
            hasMsg.signal();
        } finally {
            lock.unlock();
        }
    }


}
