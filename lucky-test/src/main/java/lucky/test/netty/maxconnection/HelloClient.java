package lucky.test.netty.maxconnection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:51 2017/6/7
 */
public class HelloClient {

    public static String host = "192.168.9.99";
//    public static String host = "127.0.0.1";

    public static int port = 8807;

    /**
     * @param args
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HelloClientInitializer());

            // 连接服务端
//            ChannelFuture f=b.connect(host,port).sync();
//            Channel ch=b.channel()


//            ChannelFuture f = b.bind(host, port).sync();
            ChannelFuture f = b.connect(host, port).sync();
            Channel ch = f.channel();
            //等待服务端链路关闭后，main函数才会退出
//            f.channel().closeFuture().sync();
//            Channel ch = b.connect(host, port).sync().channel();

            //等待客户端链路关闭
            // 控制台输入
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                String line = in.readLine();
                if (line == null) {
                    continue;
                }
                /*
                 * 向服务端发送在控制台输入的文本 并用"\r\n"结尾
                 * 之所以用\r\n结尾 是因为我们在handler中添加了 DelimiterBasedFrameDecoder 帧解码。
                 * 这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
                 * */
                ch.writeAndFlush(line + "\r\n");
            }


        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }
}
