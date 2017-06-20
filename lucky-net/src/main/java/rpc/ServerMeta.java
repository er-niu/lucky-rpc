package rpc;

import annotation.RpcMethod;
import annotation.RpcServer;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import remoting.server.FailMode;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:服务的包装类信息
 * @Date:Create in 10:58 2017/6/16
 */
public class ServerMeta {

    private String serviceName;
    private Map<String, MethodMeta> methods = new HashMap<>();


    public ServerMeta(Class<?> clazz) {
        RpcServer rpcServe = clazz.getAnnotation(RpcServer.class);
        if (rpcServe == null || StringUtils.isEmpty(rpcServe.name())) {
            this.serviceName = clazz.getSimpleName();
        } else {
            this.serviceName = rpcServe.name();
        }

        //注册服务中得方法信息

        Method[] clazzMethod = clazz.getMethods();
        for (Method mi : clazzMethod) {
            if (mi.getDeclaringClass() == Object.class) {
                continue;
            }

            RpcMethod rpcMethod = mi.getAnnotation(RpcMethod.class);

            String methodName = rpcMethod == null ? mi.getName() : rpcMethod.name();

            //服务得方法列表信息
            this.methods.put(mi.getName(), new MethodMeta(methodName, rpcMethod, rpcServe));
        }
    }


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Map<String, MethodMeta> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, MethodMeta> methods) {
        this.methods = methods;
    }

    public static class MethodMeta {
        @Getter
        private String methodName;
        //默认是转移
        @Getter
        private FailMode failMode = FailMode.FailOver;

        public MethodMeta(String name, RpcMethod rpcMethod, RpcServer rpcServer) {
            this.methodName = name;
            //优先使用rpcmethod
            if (rpcMethod != null) {
                this.failMode = rpcMethod.fail();
            } else if (rpcServer != null) {
                this.failMode = rpcServer.fail();
            }
        }
    }
}
