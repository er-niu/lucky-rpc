package rpc.client;

import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import remoting.server.FailMode;
import rpc.Invoker;
import rpc.LoadBalance.LoadBalance;
import rpc.ServerMeta;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:服务的暴漏代理类 jdk得handler
 * @Date:Create in 11:32 2017/6/16
 */
public class ClientExporter implements InvocationHandler {


    public static Logger logger = LoggerFactory.getLogger(ClientExporter.class);
    private InvokerContainer container;
    private LoadBalance balance;
    private String serviceName;
    private Map<String, ServerMeta.MethodMeta> methods;


    public ClientExporter(ServerMeta meta, InvokerContainer container) {
        this(meta, container, null);
    }


    public ClientExporter(ServerMeta meta, InvokerContainer container, String serviceName) {
        this.container = container;
        this.serviceName = (serviceName == null) ? meta.getServiceName() : serviceName;
        this.methods = meta.getMethods();

        String loadBalancer = null;
        if (container.getOptions() != null) {
            loadBalancer = container.getOptions().getConfig().get("LoadBalance");
        }
        this.balance = LoadBalance.get(loadBalancer);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (Object.class == method.getDeclaringClass()) {
            return method.invoke(this, args);
        }
        ServerMeta.MethodMeta meta = methods.get(method.getName());
        if (meta == null) {
            return method.invoke(this, args);
        }
        return invokeRemote(args,method,meta);
    }


    private Object invokeRemote(Object[] args, Method method, ServerMeta.MethodMeta methodMeta) {
        List<Invoker> invokers = container.getInvoker();
        //先获取其中得一个节点信息
        Invoker first = this.balance.select(invokers);

        try {
            return first.invoke(serviceName, methodMeta.getMethodName(), args, method.getReturnType());

        } catch (Exception e) {
            if (invokers.size() == 1) {
                throw e;
            }
            if (methodMeta.getFailMode() == FailMode.FailFast) {
                throw e;
            }

            for (Invoker invoker : invokers) {
                if (invoker == first) {
                    continue;
                }

                try {
                    return invoker.invoke(serviceName, methodMeta.getMethodName(), args, method.getReturnType());
                } catch (Exception e1) {
                    logger.info("远程调用出错，异常{}", e1);
                }
            }
        }

        //所有节点都失败
        return null;
    }
}
