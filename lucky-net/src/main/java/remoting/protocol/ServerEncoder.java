package remoting.protocol;

import remoting.data.NettyResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import remoting.serializer.KryoSerializer;
import remoting.serializer.Serializer;

import java.nio.charset.StandardCharsets;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:14 2017/6/14
 */
public class ServerEncoder extends MessageToByteEncoder<NettyResponse> {

    private Serializer serializer = new KryoSerializer();
    private static final byte[] RESPONSE_HEADER = "NettyResponse".getBytes(StandardCharsets.US_ASCII);
    private static final byte[] CRLF = new byte[]{'\r', '\n'};

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyResponse nettyResponse, ByteBuf byteBuf) throws Exception {
        // "NettyResponse " + [length] + "\r\n" + [data]
        byte[] bytes = serializer.writeObject(nettyResponse);
        byteBuf.writeBytes(RESPONSE_HEADER);
        byteBuf.writeBytes(Integer.toString(bytes.length).getBytes(StandardCharsets.US_ASCII));
        byteBuf.writeBytes(CRLF);
        byteBuf.writeBytes(bytes);
    }
}
