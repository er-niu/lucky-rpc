package remoting.client.handler;

import remoting.client.ClientOptions;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.pool.ChannelPoolHandler;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import remoting.protocol.ClientDecoder;
import remoting.protocol.ClientEncoder;
import remoting.protocol.ClientHandler;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:50 2017/6/15
 */
public class ClientChannelPoolHandler implements ChannelPoolHandler {

    private static Logger logger= LoggerFactory.getLogger(ClientHandler.class);


    private ClientOptions options;
    private static final String DECODE="DECODE";
    private static final String ENCODE="ENCODE";
    private static final String IDLESTATE="IDLESTATE";
    private static final String PROCESS="PROCESS";


    public ClientChannelPoolHandler(ClientOptions options){
        this.options=options;
    }
    @Override
    public void channelReleased(Channel channel) throws Exception {

        logger.info("channelReleased");
    }

    @Override
    public void channelAcquired(Channel channel) throws Exception {
        logger.info("channelReleased");
    }

    @Override
    public void channelCreated(Channel channel) throws Exception {

        logger.info("start to channel create");

        ChannelPipeline ch=channel.pipeline();

        ClientIdleHandler idleHandler=new ClientIdleHandler(this.options.getReadTime(),this.options.getWriteTime(),this.options.getKeepAliveTime());
        ch.addLast(IDLESTATE,idleHandler);
        ch.addLast(ENCODE,new ClientEncoder());
        ch.addLast(DECODE,new ClientDecoder());
        ch.addLast(PROCESS,new ClientHandler());
    }
}
