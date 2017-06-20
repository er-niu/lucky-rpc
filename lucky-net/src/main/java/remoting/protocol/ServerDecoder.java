package remoting.protocol;

import remoting.data.NettyRequest;

import java.nio.charset.StandardCharsets;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:48 2017/6/13
 */
public class ServerDecoder extends ProtocolDecoder {
    private static final byte[] REQUEST_HEADER = "NettyRequest".getBytes(StandardCharsets.US_ASCII);

    public ServerDecoder() {
        super(NettyRequest.class, REQUEST_HEADER);
    }

}
