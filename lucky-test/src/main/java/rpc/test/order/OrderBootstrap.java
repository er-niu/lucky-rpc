package rpc.test.order;

import rpc.RpcApplication;
import rpc.options.RpcServerOptions;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 9:02 2017/6/20
 */

public class OrderBootstrap {
    /**
     * 主函数
     *
     * @param args
     */
    public static void main(String[] args) {

        System.setProperty("com.lucky.logger","log4j");
        RpcServerOptions rpcServerOptions=new RpcServerOptions();
        rpcServerOptions.setRegister(true);
        rpcServerOptions.setAddress("192.168.9.196");
        rpcServerOptions.setPort(8765);
        rpcServerOptions.setName("cmc.lucky.order");
        rpcServerOptions.getConfig().put("ServicePackage","rpc.test.order");
        RpcApplication application = new RpcApplication(OrderBootstrap.class, args,rpcServerOptions);
        application.run();
    }
}
