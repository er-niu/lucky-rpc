package rpc;

import exception.RpcException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.pool.ChannelPool;
import io.netty.util.concurrent.Future;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import remoting.client.ClientChannel;
import remoting.client.ClientOptions;
import remoting.data.NettyRequest;
import remoting.data.NettyResponse;


/**
 * @Author:chaoqiang.zhou
 * @Description:rpc远程调用类
 * @Date:Create in 16:32 2017/6/15
 */
public class RpcInvoker implements Invoker {


    private static Logger logger = LoggerFactory.getLogger(RpcInvoker.class);
    private ClientOptions options;
    private ChannelPool pool;

    public RpcInvoker(ChannelPool pool, ClientOptions options) {
        this.pool = pool;
        this.options = options;
    }


    @Override
    public Object invoke(String service, String methodName, Object[] args, Class<?> returnType) {
        NettyRequest request = null;
        NettyResponse response = null;
        ClientChannel channel = null;
        try {
            try {
                request = createRequest(service, methodName, args);
            } catch (Exception e) {
                logger.error("create request error{}", e);
                throw new RpcException(RpcException.NETWORK_EXCEPTION, e);
            }
            //获取连接操作
            Future<Channel> future = pool.acquire().awaitUninterruptibly();
            if (!future.isSuccess()) {
                throw new RpcException(RpcException.NETWORK_EXCEPTION, future.cause());
            }


            //发送请求操作
            channel = (ClientChannel) future.getNow();
            //把返回值值空
            channel.reset();
            //发送请求
            ChannelFuture channelFuture = channel.writeAndFlush(request).awaitUninterruptibly();
            if (!channelFuture.isSuccess()) {
                throw new RpcException(RpcException.NETWORK_EXCEPTION, future.cause());
            }


            try {
                response = channel.get(this.options.getReadTime());
            } catch (Exception e) {
                throw new RpcException(RpcException.UNKNOWN_EXCEPTION, e);
            }


            //进行判断操作
            if (response == null) {
                //获取超时
                channel.close();
                throw new RpcException(RpcException.TIMEOUT_EXCEPTION);
            } else if (response.isSuccess()) {
                //获取到了返回得请求
                return response.getResult();
            }
        } finally {
            if (channel != null) {
                pool.release(channel);
            }
        }

        return null;
    }


    //创建客户端消息
    private NettyRequest createRequest(String service, String method, Object[] args) {
        NettyRequest request = new NettyRequest();
        request.setArguments(args);
        request.setMethodName(method);
        request.setServiceName(service);
        return request;
    }
}
