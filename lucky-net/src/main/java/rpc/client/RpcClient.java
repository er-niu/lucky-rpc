package rpc.client;

import rpc.ServerMeta;
import rpc.ServerMetaFactory;

import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:chaoqiang.zhou
 * @Description:客户端远程代理类配置
 * @Date:Create in 10:52 2017/6/16
 */
public class RpcClient {
    //代理类缓存
    //key：server+serviceName
    private static final ConcurrentHashMap<String, Object> proxies = new ConcurrentHashMap<>();

    private RpcClient() {
        //单例模式
    }


    //获取代理对象
    public static <T> T get(String server, Class<T> clazz) {
        ServerMeta meta = ServerMetaFactory.get(clazz);
        String key = server + "." + meta.getServiceName();
        Object obj = proxies.get(key);
        if (obj != null) {
            return (T) obj;
        }
        InvokerContainer container = InvokerContainer.get(server);
        T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ClientExporter(meta, container));
        proxies.put(key, proxy);
        return proxy;
    }


    public static boolean isProxy(Object instance) {
        if (Proxy.isProxyClass(instance.getClass())) {
            return Proxy.getInvocationHandler(instance) instanceof ClientExporter;
        }

        return false;
    }
}
