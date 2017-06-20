package lucky.test.netty.rpc;

import io.netty.channel.pool.ChannelPool;
import remoting.client.ClientChanelPoolFactory;
import remoting.client.ClientOptions;
import rpc.Provider;
import rpc.RpcInvoker;
import rpc.options.RpcClientOptions;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:47 2017/6/20
 */
public class Client {
    private static final ClientChanelPoolFactory poolMap = new ClientChanelPoolFactory();

    public static void main(String[] args) {
        System.setProperty("com.lucky.logger", "log4j");

        Provider provider = new Provider();
        provider.setAddress("192.168.9.196");
        provider.setPort(8865);
        RpcClientOptions rpcClientOptions = new RpcClientOptions(provider);

        ClientOptions clientOptions = new ClientOptions(rpcClientOptions);
        ChannelPool channelPool = poolMap.get(clientOptions);


        RpcInvoker rpcInvoker = new RpcInvoker(channelPool, clientOptions);
        String serviceName = "test";
        String methodName = "test";
        Object[] params = new Object[]{1, 3};
        Object obj = rpcInvoker.invoke(serviceName, methodName, params, Integer.class);
        System.out.println(obj);
    }
}
