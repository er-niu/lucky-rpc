package lucky.test.netty.maxconnection;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:07 2017/6/10
 */

@ChannelHandler.Sharable
public class SessionHandler extends ChannelInboundHandlerAdapter {

    private DefaultChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.add(channel);
        //就不会再次的调用后面得channelactive了，如果想再次调用需要
        //通过责任链向后面传递
//        ctx.fireChannelActive();
//        super.channelActive(ctx);
    }


    public DefaultChannelGroup getChannels() {
        return channels;
    }

    public void setChannels(DefaultChannelGroup channels) {
        this.channels = channels;
    }
}
