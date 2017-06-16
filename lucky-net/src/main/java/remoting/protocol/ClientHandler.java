package remoting.protocol;

import remoting.client.ClientChannel;
import remoting.data.NettyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;

/**
 * @Author:chaoqiang.zhou
 * @Description:客户端信息处理操作
 * @Date:Create in 16:01 2017/6/15
 */
public class ClientHandler extends SimpleChannelInboundHandler<NettyResponse> {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, NettyResponse nettyResponse) throws Exception {
        ClientChannel clientChannel = (ClientChannel) channelHandlerContext.channel();
        //服务端返回值set到socketniochanel中
        clientChannel.set(nettyResponse);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("the error {},channel{}", cause, ctx.channel());
    }
}
