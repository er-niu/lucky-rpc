package com.lucky.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import rpc.RpcApplication;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:19 2017/6/20
 */

@SpringBootApplication
public class OrderBootStrap {
    /**
     * 主函数
     *
     * @param args
     */
    public static void main(String[] args) {

//        System.setProperty("com.lucky.logger", "log4j");
//        RpcServerOptions rpcServerOptions = new RpcServerOptions();
//        rpcServerOptions.setRegister(true);
//        rpcServerOptions.setAddress("192.168.9.196");
//        rpcServerOptions.setPort(8765);
//        rpcServerOptions.setName("com.lucky.order.service");
//        rpcServerOptions.getConfig().put("ServicePackage", "com.lucky.service.iface");
//        RpcApplication application = new RpcApplication(OrderBootStrap.class, args, rpcServerOptions);
//        application.run();
//        OrderService orderService = application.getBean(OrderService.class);
//        System.out.println(orderService.getOrderInfo());
//        System.out.println(orderService.getOrderInfos("23"));
        RpcApplication application = new RpcApplication(OrderBootStrap.class, args);
        application.run();
    }
}
