package rpc;

import remoting.client.ClientChanelPoolFactory;
import remoting.client.ClientChannelPool;
import remoting.client.ClientOptions;
import rpc.options.RpcClientOptions;

/**
 * @Author:chaoqiang.zhou
 * @Description:创建远程调用invoker信息
 * @Date:Create in 17:05 2017/6/15
 */
public class InvokerFactory {
    private static final ClientChanelPoolFactory poolMap = new ClientChanelPoolFactory();

    private InvokerFactory(){
        //单例模式
    }


    public static RpcInvoker get(RpcClientOptions options){
        ClientOptions clientOptions=new ClientOptions(options);
        ClientChannelPool pool=poolMap.get(clientOptions);
        return new RpcInvoker(pool,clientOptions);
    }
}
