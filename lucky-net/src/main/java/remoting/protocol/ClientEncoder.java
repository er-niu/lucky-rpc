package remoting.protocol;

import remoting.data.NettyRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import remoting.serializer.KryoSerializer;
import remoting.serializer.Serializer;

import java.nio.charset.StandardCharsets;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:58 2017/6/15
 */
public class ClientEncoder extends MessageToByteEncoder<NettyRequest> {
    private Serializer serializer = new KryoSerializer();
    private static final byte[] REQUEST_HEADER = "NettyRequest".getBytes(StandardCharsets.US_ASCII);
    private static final byte[] CRLF = new byte[]{'\r', '\n'};

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyRequest request, ByteBuf byteBuf) throws Exception {
        // "NettyResponse " + [length] + "\r\n" + [data]
        byte[] bytes = serializer.writeObject(request);
        byteBuf.writeBytes(REQUEST_HEADER);
        byteBuf.writeBytes(Integer.toString(bytes.length).getBytes(StandardCharsets.US_ASCII));
        byteBuf.writeBytes(CRLF);
        byteBuf.writeBytes(bytes);
    }
}
