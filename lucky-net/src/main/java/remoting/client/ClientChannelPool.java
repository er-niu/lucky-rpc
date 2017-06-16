package remoting.client;

import remoting.client.handler.ClientChannelPoolHandler;
import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelHealthChecker;
import io.netty.channel.pool.ChannelPool;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;

/**
 * @Author:chaoqiang.zhou
 * @Description:客户端的连接池，防止请求过多，创建过多得bootstrap对象
 * @Date:Create in 15:21 2017/6/15
 */
public class ClientChannelPool implements ChannelPool {

    //固定的channel连接池操作
    private FixedChannelPool pool;

    public ClientChannelPool(ClientOptions options) {
        NettyClient nettyClient = new NettyClient(options);
        pool = new FixedChannelPool(nettyClient, new ClientChannelPoolHandler(options), ChannelHealthChecker.ACTIVE, FixedChannelPool.AcquireTimeoutAction.FAIL, options.getAcquireTime(), options.getMaxConnections(), options.getMaxPendingAcquires());

    }

    @Override
    public Future<Channel> acquire() {
        return pool.acquire();
    }

    @Override
    public Future<Channel> acquire(Promise<Channel> promise) {
        return pool.acquire(promise);
    }

    @Override
    public Future<Void> release(Channel channel) {
        return pool.release(channel);
    }

    @Override
    public Future<Void> release(Channel channel, Promise<Void> promise) {
        return pool.release(channel, promise);
    }

    @Override
    public void close() {
        pool.close();
    }
}
