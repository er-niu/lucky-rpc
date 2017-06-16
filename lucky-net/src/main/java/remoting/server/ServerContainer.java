package remoting.server;

import annotation.RpcMethod;
import annotation.RpcServer;
import com.esotericsoftware.reflectasm.MethodAccess;
import exception.RpcException;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author:chaoqiang.zhou
 * @Description:服务端方法执行器的缓存配置，也是一个本地配置的配置中心
 * @Date:Create in 14:01 2017/6/14
 */
public class ServerContainer {

    private static Logger logger = LoggerFactory.getLogger(ServerContainer.class);
    //serviceName+MethodName:启动得时候就直接注册了，用来服务与远程调用
    private static final HashMap<String, MethodExecutor> executors = new HashMap<>();
    //用来服务于注册中心列表服务
    private Map<String, ServiceInfo> services = new TreeMap<>();

    public void registerService(Object instance) {
        Class<?> clazz = instance.getClass();
        this.registerService(clazz, instance);
    }

    public void registerService(String name, Object instance) {
        Class<?> clazz = instance.getClass();
        RpcServer rpcService = clazz.getAnnotation(RpcServer.class);
        String description = rpcService == null ? "" : rpcService.description();
        this.registerService(clazz, instance, name, description);
    }

    public void registerService(Class<?> clazz, Object instance) {
        RpcServer rpcServer = clazz.getAnnotation(RpcServer.class);
        String description = rpcServer == null ? "" : rpcServer.description();
        String name = null;
        if (rpcServer != null) {
            name = rpcServer.name();
        }

        if (StringUtils.isEmpty(name)) {
            name = clazz.getSimpleName();
        }
        this.registerService(clazz, instance, name, description);

    }

    private void registerService(Class<?> clazz, Object instance, String name, String description) {
        logger.info("register service:{}", name);
        MethodAccess access = MethodAccess.get(clazz);
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            if (m.getDeclaringClass() == Object.class) {
                continue;
            }

            try {
                int index = access.getIndex(m.getName());
                String methodName = getMethodName(m);
                MethodExecutor mi = new MethodExecutor(instance, access, index);
                executors.put(buildKey(name, methodName), mi);
                logger.info("register service:{}{}", name, methodName);

            } catch (Exception e) {
                logger.error("register service:{}{},failed", name, methods);
            }
        }

        ServiceInfo serviceInfo = new ServiceInfo(clazz, name, description);
        this.services.put(serviceInfo.getName(), serviceInfo);
    }

    public Object execute(String service, String method, Object[] args) {
        MethodExecutor executor = executors.get(buildKey(service, method));
        if (executor == null) {
            throw new RpcException(RpcException.BIZ_EXCEPTION);
        }
        return executor.invoke(args);
    }

    public Map<String, ServiceInfo> getServices() {
        return services;
    }
    public MethodExecutor getExecutor(String service, String method) {
        return executors.get(buildKey(service, method));
    }
    private String buildKey(String serviceName, String methodName) {
        return serviceName + "." + methodName;
    }

    public static String getMethodName(Method method) {
        return getMethodName(method, method.getAnnotation(RpcMethod.class));
    }


    public static String getMethodName(Method method, RpcMethod rpcMethod) {
        String name = null;
        if (rpcMethod != null) {
            name = rpcMethod.name();
            return name;
        }

        if (StringUtils.isNotEmpty(method.getName())) {
            return method.getName();
        }
        return null;
    }


    //方法得动态反射执行器信息
    public static class MethodExecutor {
        private MethodAccess access;
        private int index;
        private Object object;

        public MethodExecutor(Object obj, MethodAccess access, int index) {
            this.object = obj;
            this.access = access;
            this.index = index;
        }

        public Object invoke(Object[] args) {
            return access.invoke(object, index, args);
        }

        public Class[] getParameterTypes() {
            return access.getParameterTypes()[index];
        }

        public Class getReturnType() {
            return access.getReturnTypes()[index];
        }

    }
}

