package com.lucky.service.spring;

import com.lucky.service.iface.OrderService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import rpc.client.RpcClient;


@Configuration
@Lazy
@Order(Ordered.LOWEST_PRECEDENCE)
public class OrderServiceProxyAutoConfig {
    private static final String SERVICE = "com.lucky.order.service";

    /**
     * 订单服务
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public OrderService orderServiceProxy() {
        return RpcClient.get(SERVICE, OrderService.class);
    }

}
