package rpc.LoadBalance;

import rpc.Invoker;

import java.util.List;
import java.util.Random;

/**
 * @Author:chaoqiang.zhou
 * @Description:参照dubbo，没考虑那么负载，dubbo中可以指定权重
 * @Date:Create in 11:40 2017/6/16
 */
public class RandomLoadBalance implements LoadBalance {
    private final Random random = new Random();

    public static final String NAME="random";
    @Override
    public Invoker select(List<Invoker> invokers) {
        int length = invokers.size(); // 总个数
        return invokers.get(random.nextInt(length));
    }
}
