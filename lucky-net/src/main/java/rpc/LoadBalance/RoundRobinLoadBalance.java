package rpc.LoadBalance;

import rpc.Invoker;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:48 2017/6/16
 */
public class RoundRobinLoadBalance implements LoadBalance {
    private AtomicInteger counter = new AtomicInteger();

    @Override
    public Invoker select(List<Invoker> invokers) {
        int index = Math.abs(counter.getAndIncrement() % invokers.size());
        return invokers.get(index);
    }
}
