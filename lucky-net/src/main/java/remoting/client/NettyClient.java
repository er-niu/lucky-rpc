package remoting.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultMessageSizeEstimator;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @Author:chaoqiang.zhou
 * @Description:netty客户端连接类操作 参考：http://www.th7.cn/Program/java/201702/1098024.shtml
 * @Date:Create in 15:24 2017/6/15
 */
public class NettyClient extends Bootstrap {


    private ClientOptions options;


    public NettyClient(ClientOptions options) {
        this.options = options;
        this.group(new NioEventLoopGroup(options.getWorkThreads()));
        //数据最终从该channel中进行输出
        this.channel(ClientChannel.class);
        this.remoteAddress(options.getAddress(), options.getPort());
        //传输得参数设置

        this.option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, options.getConnectTimeOut())
                .option(ChannelOption.SO_RCVBUF, options.getReceiveBufferSize())
                .option(ChannelOption.SO_SNDBUF, options.getSendBufferSize())
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.MESSAGE_SIZE_ESTIMATOR, new DefaultMessageSizeEstimator(options.getReceiveBufferSize()));
    }

    public ClientOptions getOptions() {
        return options;
    }

    public void setOptions(ClientOptions options) {
        this.options = options;
    }
}
