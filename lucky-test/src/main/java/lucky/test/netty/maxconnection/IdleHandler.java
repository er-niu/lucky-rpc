package lucky.test.netty.maxconnection;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:09 2017/6/10
 */

/**
 * Sharable表示此对象在channel间共享
 * handler类是我们的具体业务类
 */

public class IdleHandler extends ChannelDuplexHandler {

//    private static Logger logger = LoggerFactory.getLogger(IdleHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                System.out.println("channel is closed because of idle timeout");
//                logger.info("channel {} is closed because of idle timeout.", ctx.channel());
//                ctx.fireExceptionCaught(ReadTimeoutException.INSTANCE);
                ctx.close();
            }
        }
    }
}
