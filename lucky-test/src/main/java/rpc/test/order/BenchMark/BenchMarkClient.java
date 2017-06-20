package rpc.test.order.BenchMark;

import com.lucky.service.iface.OrderService;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import org.springframework.context.ApplicationContext;
import rpc.RpcApplication;
import rpc.options.RpcServerOptions;
import rpc.test.order.OrderBootstrap;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 2016-1-9的最新一次测试结果(小数据包1亿+次同步调用):
 * ------------------------------------------------------------------
 * 测试机器:
 * server端(一台机器)
 *      cpu型号: Intel(R) Core(TM) CPU           X3430  @ 2.50GHz
 *      cpu cores: 4
 *
 * client端(同服务端，是一台机器)
 * 网络环境: 局域网
 * ------------------------------------------------------------------
 * 测试结果:
 *  Request count: 847065, time: 167 second, qps: 5072
 *
 *
 * 2017年6月20日：
 * 通信层可以再优化下，去除一些io线程，没那么大得并发量
 *
 */
public class BenchMarkClient {
    public static OrderService orderService = null;

    static {
        System.setProperty("com.lucky.logger", "log4j");

    }

    public static final Logger logger = LoggerFactory.getLogger(BenchMarkClient.class);

    public static void main(String[] args) {
        logger.warn("hah");
        int processors = Runtime.getRuntime().availableProcessors();
        RpcServerOptions rpcServerOptions = new RpcServerOptions();
        rpcServerOptions.setRegister(true);
        rpcServerOptions.setAddress("192.168.9.196");
        rpcServerOptions.setPort(8768);
        rpcServerOptions.setName("cmc.lucky.test");
        rpcServerOptions.getConfig().put("ServicePackage", "rpc.test.order.iface");
        RpcApplication application = new RpcApplication(OrderBootstrap.class, args, rpcServerOptions);
        ApplicationContext context = application.run();
        orderService = application.getBean(OrderService.class);
//        System.out.println(orderService.getOrderInfo());
        syncCall(processors);
    }

    private static void syncCall(int processors) {
        for (int i = 0; i < 10000; i++) {
            try {
                orderService.getOrderInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        final int t = 5000;
        final int step = 6;
        long start = System.currentTimeMillis();
        final CountDownLatch latch = new CountDownLatch(processors << step);
        final AtomicLong count = new AtomicLong();
        for (int i = 0; i < (processors << step); i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    for (int i = 0; i < t; i++) {
                        try {
                            orderService.getOrderInfo();
                            if (count.getAndIncrement() % 1000 == 0) {
                                logger.warn("count=" + count.get());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
            logger.warn("count=" + count.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long second = (System.currentTimeMillis() - start) / 1000;
        System.out.println("Request count: " + count.get() + ", time: " + second + " second, qps: " + count.get() / second);
    }
}
