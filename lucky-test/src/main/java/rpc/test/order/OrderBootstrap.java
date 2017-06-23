package rpc.test.order;

import com.lucky.service.iface.OrderService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import rpc.RpcApplication;
import rpc.options.RpcServerOptions;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 9:02 2017/6/20
 */

@SpringBootApplication()
public class OrderBootstrap {
    /**
     * 主函数
     *
     * @param args
     */
    public static void main(String[] args) {

        System.setProperty("com.lucky.logger", "log4j");
//        RpcServerOptions rpcServerOptions = new RpcServerOptions();
//        rpcServerOptions.setRegister(true);
//        rpcServerOptions.setAddress("192.168.9.196");
//        rpcServerOptions.setPort(8980);
//        rpcServerOptions.setName("cmc.lucky.test");
//        rpcServerOptions.getConfig().put("ServicePackage", "rpc.test.order.iface");
        RpcApplication application = new RpcApplication(OrderBootstrap.class, args);
        ApplicationContext context = application.run();
        OrderService orderService = application.getBean(OrderService.class);
        System.out.println(orderService.getOrderInfo());
        System.out.println(orderService.getOrderInfos("23"));
//        SpringApplication.run(OrderBootstrap.class, args);
    }
}
