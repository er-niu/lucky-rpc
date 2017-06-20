package remoting.protocol;

import remoting.data.NettyResponse;

import java.nio.charset.StandardCharsets;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:00 2017/6/15
 */
public class ClientDecoder extends ProtocolDecoder {
    private static final byte[] RESPONSE_HEADER = "NettyResponse".getBytes(StandardCharsets.US_ASCII);

    public ClientDecoder() {
        super(NettyResponse.class, RESPONSE_HEADER);
    }

}
