package rpc;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:32 2017/6/15
 */
public interface Invoker {

    Object invoke(String service,String methodName,Object[] args,Class<?> returnType);
}
