package remoting.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import remoting.serializer.KryoSerializer;
import remoting.serializer.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:39 2017/6/13
 */
public class ProtocolDecoder extends ByteToMessageDecoder {

    private byte[] header;
    private int headerLength;
    private State state = State.HEADER;
    private int length;
    private Class clazz;
    //todo:这里写死了，后期考虑用设计模式脱离出来
    private Serializer serializer = new KryoSerializer();



    public ProtocolDecoder(Class clazz, byte[] header) {
        this.header = header;
        this.headerLength = header.length;
        this.clazz = clazz;
    }


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        decode(byteBuf, list);
    }


    private void decode(ByteBuf buf, List<Object> out) throws Exception {
        //顺序按照包的结构进行读取，后期可以考虑换netty中得其他channelinbound操作
        switch (state) {
            case HEADER:
                decodeHeader(buf, out);
                break;
            case LENGTH:
                decodeLength(buf, out);
                break;
            case CRLF:
                decodeCRLF(buf, out);
                break;
            case BODY:
                decodeBody(buf, out);
                break;
            default:
                throw new Exception("invalid parse state: " + state);
        }
    }


    private void decodeBody(ByteBuf buf, List<Object> out) throws Exception {
        if (buf.readableBytes() >= length) {
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            Object msg = serializer.readObject(bytes, clazz);
            out.add(msg);
            state = State.HEADER;
            // 这里其实 netty 内部也会自动处理剩余消息
            if (buf.readableBytes() > 0) {
                decode(buf, out);
            }
        }
    }

    private void decodeCRLF(ByteBuf buf, List<Object> out) throws Exception {
        if (buf.readableBytes() >= 2) {
            if (buf.readByte() != '\r' || buf.readByte() != '\n') {
                throw new Exception("expect: \\r\\n");
            }
            state = State.BODY;
            decode(buf, out);
        }
    }

    private void decodeLength(ByteBuf buf, List<Object> out) throws Exception {
        if (buf.readableBytes() > 0) {
            int number = buf.bytesBefore((byte) '\r');
            if (number != -1) {
                byte[] bytes = new byte[number];
                buf.readBytes(bytes);
                length = Integer.parseInt(new String(bytes, StandardCharsets.US_ASCII));
                state = State.CRLF;
                decode(buf, out);
            }
        }
    }

    private void decodeHeader(ByteBuf buf, List<Object> out) throws Exception {
        if (buf.readableBytes() >= headerLength) {
            byte[] bytes = new byte[headerLength];
            buf.readBytes(bytes);
            if (!Arrays.equals(header, bytes)) {
                throw new Exception("expect: " + new String(header, StandardCharsets.US_ASCII));
            }
            state = State.LENGTH;
            decode(buf, out);
        }


    }


    private enum State {
        HEADER, LENGTH, CRLF, BODY;
    }
}
