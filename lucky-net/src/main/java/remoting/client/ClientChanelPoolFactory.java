package remoting.client;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:chaoqiang.zhou
 * @Description:每一份地址ip+端口号一个channelpool
 * @Date:Create in 17:07 2017/6/15
 */
public class ClientChanelPoolFactory {

    private final ConcurrentHashMap<InetSocketAddress, ClientChannelPool> poolMap = new ConcurrentHashMap<>();
    public final ClientChannelPool get(ClientOptions options) {
        InetSocketAddress address = new InetSocketAddress(options.getAddress(), options.getPort());
        ClientChannelPool pool = this.poolMap.get(address);
        if (pool == null) {
            pool = createPool(options);
            ClientChannelPool old = this.poolMap.putIfAbsent(address, pool);
            if (old != null) {
                pool.close();
                pool = old;
            }
        }

        return pool;
    }
    //一个pool是一个连接操作
    private ClientChannelPool createPool(ClientOptions clientOptions) {
        return new ClientChannelPool(clientOptions);
    }
}
