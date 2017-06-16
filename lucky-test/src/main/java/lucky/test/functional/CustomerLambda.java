package lucky.test.functional;

import java.util.function.Consumer;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:42 2017/6/14
 */

@FunctionalInterface
public interface CustomerLambda<T> {

    T customFunction(Consumer<T> consumer);
}
