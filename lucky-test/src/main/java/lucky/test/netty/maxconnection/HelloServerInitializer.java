package lucky.test.netty.maxconnection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:44 2017/6/7
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    public SessionHandler sessionHandler;

    public HelloServerInitializer(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        System.out.println("目前得连接数目是" + sessionHandler.getChannels().size());
        System.out.println(sessionHandler.getChannels().size());
        if (sessionHandler.getChannels().size() > 0) {
            System.out.println("目前连接数，连接数超过上限" + sessionHandler.getChannels().size());
            ch.close();
            return;
        }

        ChannelPipeline pipeline = ch.pipeline();
        IdleStateHandler idleStateHandler = new IdleStateHandler(30,
                30, 30);
        pipeline.addLast("session_handler", sessionHandler);
        pipeline.addLast("idle_handler", idleStateHandler);
        pipeline.addLast("idel_process", new IdleHandler());

        // 以("\n")为结尾分割的 解码器
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        // 字符串解码 和 编码
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());

        // 自己的逻辑Handler
        pipeline.addLast("handler", new HelloServerHandler());

    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("开始1");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("开始2");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("开始3");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("开始4");
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("开始5");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("开始6");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("开始7");
        super.channelWritabilityChanged(ctx);
    }
}
