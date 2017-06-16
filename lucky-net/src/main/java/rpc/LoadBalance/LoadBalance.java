package rpc.LoadBalance;

import org.apache.commons.lang3.StringUtils;
import rpc.Invoker;

import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:38 2017/6/16
 */
public interface LoadBalance {

    Invoker select(List<Invoker> invokers);

    static LoadBalance get(String name) {
        if (StringUtils.isEmpty(name)) {
            return new RoundRobinLoadBalance();
        }

        if (name.equals("Round")) {
            return new RoundRobinLoadBalance();
        } else {
            return new RandomLoadBalance();
        }
    }
}
