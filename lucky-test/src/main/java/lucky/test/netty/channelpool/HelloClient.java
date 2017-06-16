package lucky.test.netty.channelpool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.ChannelHealthChecker;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.util.concurrent.Future;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:51 2017/6/7
 */
public class HelloClient {


    private static FixedChannelPool pool;

    private static SimpleClientChannel channel;
    private static String responseMessage;
    private static ReentrantLock lock = new ReentrantLock();

    private static Condition hasMessage = lock.newCondition();

    public static String host = "127.0.0.1";
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
            b.group(new NioEventLoopGroup(2));
            b.channel(SimpleClientChannel.class);
            b.remoteAddress(host, port);
            pool = new FixedChannelPool(b,
                    new SimpleChannelPoolHandler(),
                    ChannelHealthChecker.ACTIVE,
                    FixedChannelPool.AcquireTimeoutAction.FAIL,
                    1000,
                    1000,
                    1000);


            // 获取连接
            Future<Channel> future = pool.acquire().awaitUninterruptibly();
            if (!future.isSuccess()) {
                String error = future.cause().getMessage();
                System.out.println(error);
            }


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
                // 发送请求
                channel = (SimpleClientChannel) future.getNow();
                System.out.println("低昂钱channel为");
                ChannelFuture channelFuture = channel.writeAndFlush(line + "\r\n").awaitUninterruptibly();
                if (!channelFuture.isSuccess()) {
                    String error = future.cause().getMessage();
                    System.out.println(error);
                }
                try {
                    responseMessage = channel.get(1000);
                } catch (Exception e) {
                    String error = e.getMessage();
                    System.out.println(error);
                }

                System.out.println(responseMessage);
                try {
                    long end = System.currentTimeMillis() + 1000;
                    long time = 1000;
                    while (responseMessage == null) {
                        boolean ok = hasMessage.await(time, TimeUnit.MILLISECONDS);
                        if (ok || (time = end - System.currentTimeMillis()) <= 0) {
                            break;
                        }
                    }
                } finally {
                    lock.unlock();
                }
                // 等待响应
                System.out.println(responseMessage);
            }


        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }

    static class SimpleChannelPoolHandler implements ChannelPoolHandler {

        @Override
        public void channelReleased(Channel channel) throws Exception {

        }

        @Override
        public void channelAcquired(Channel channel) throws Exception {

        }

        @Override
        public void channelCreated(Channel channel) throws Exception {

        }
    }
}
