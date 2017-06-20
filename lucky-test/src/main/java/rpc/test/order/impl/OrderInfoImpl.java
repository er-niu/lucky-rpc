package rpc.test.order.impl;

import rpc.test.order.iface.OrderInfoInterface;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 8:57 2017/6/20
 */
public class OrderInfoImpl implements OrderInfoInterface {
    @Override
    public String getOrderInfo(String orderId) {
        System.out.println(orderId);
        return orderId;
    }
}
