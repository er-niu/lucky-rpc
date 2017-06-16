package remoting.server.handler;

import remoting.data.NettyRequest;
import remoting.data.NettyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import remoting.server.LocalNettyExecutor;
import remoting.server.NettyRequestProcessor;
import remoting.server.RemotingServerImpl;

import java.util.concurrent.RejectedExecutionException;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:50 2017/6/14
 */
public class ServerHandler extends SimpleChannelInboundHandler<NettyRequest> {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    private NettyRequestProcessor processor;
    private RemotingServerImpl server;


    public ServerHandler(RemotingServerImpl server) {
        this.server = server;
        this.processor = new LocalNettyExecutor(server.getServerContainer());
    }

    //线程池中进行处理业务逻辑操作
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, NettyRequest nettyRequest) throws Exception {
        try {
            server.getExecutors().submit(new ServerTask(processor, nettyRequest, channelHandlerContext));
        } catch (RejectedExecutionException e) {
            //这里应该就没有异常了，除非。连接数过多，channel自动关闭
            NettyResponse response = NettyResponse.failed(e);
            channelHandlerContext.writeAndFlush(response);
            logger.error("thread pool is full");
        }
    }


    //channel出现异常信息操作得时候自动触发
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("the error {},channel,{}",cause,ctx);
        ctx.close();
        //后期可以考虑根据不同得异常进行响应得操作
    }

    private static class ServerTask implements Runnable {

        private final NettyRequestProcessor processor;
        private final NettyRequest request;
        private final ChannelHandlerContext ctx;

        public ServerTask(NettyRequestProcessor processor, NettyRequest request, ChannelHandlerContext ctx) {
            this.processor = processor;
            this.request = request;
            this.ctx = ctx;
        }

        @Override
        public void run() {
            NettyResponse response = null;

            try {
                response = this.processor.processRequest(ctx, request);
            } catch (Exception e) {
                response = NettyResponse.failed(e);
                logger.error("request processing failed e", e);
            }
            //返回到服务端的信息
            ctx.channel().writeAndFlush(response);

        }
    }


}
