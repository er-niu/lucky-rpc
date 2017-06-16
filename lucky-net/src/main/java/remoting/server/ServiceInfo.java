package remoting.server;

import annotation.RpcMethod;
import annotation.RpcParameter;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:服务元数据信息
 * @Date:Create in 14:48 2017/6/14
 */
@Getter
@Setter
public class ServiceInfo {

    private String name;
    private String description;
    private Map<String, MethodInfo> methods;

    //整个服务全部注册
    public ServiceInfo(Class<?> clazz, String name, String description) {
        this.name = name;
        this.description = description;
        this.methods = new HashMap<>();

        Method[] clazzMethod = clazz.getMethods();
        for (Method m : clazzMethod) {
            if (m.getDeclaringClass() == Object.class) {
                //排除object得方法
                continue;
            }
            RpcMethod rpcMethod = m.getAnnotation(RpcMethod.class);

            MethodInfo mi = new MethodInfo();

            mi.name = rpcMethod == null ? m.getName() : rpcMethod.name();
            mi.description = rpcMethod == null ? null : rpcMethod.description();
            mi.returnType = getReturn(m);
            mi.parameters = getParameters(m);
            this.methods.put(mi.name, mi);
        }
    }


    private static ParameterInfo getReturn(Method method) {
        Class<?> returnType = method.getReturnType();
        if (returnType == null || returnType == Void.class) {
            return null;
        }
        return getParameter(returnType, method.getAnnotation(RpcParameter.class));
    }


    private static List<ParameterInfo> getParameters(Method method) {
        Parameter[] parameters = method.getParameters();
        List<ParameterInfo> parameterInfos = new ArrayList<>(parameters.length);
        if (parameters.length > 0) {
            for (Parameter p : parameters) {
                ParameterInfo pi = getParameter(p.getType(), p.getAnnotation(RpcParameter.class));
                if (StringUtils.isBlank(pi.name)) {
                    pi.name = p.getName();
                }
                parameterInfos.add(pi);
            }
        }

        return parameterInfos;
    }


    private static ParameterInfo getParameter(Class<?> paramType, RpcParameter rpcParameter) {
        ParameterInfo pi = new ParameterInfo();
        pi.type = paramType;
        if (rpcParameter != null) {
            if (StringUtils.isNoneEmpty(rpcParameter.name())) {
                pi.name = rpcParameter.name();
            }
            pi.description = rpcParameter.description();
        }

        return pi;
    }

    @Getter
    @Setter
    public static class MethodInfo {
        // 名称
        private String name;

        // 描述
        private String description;

        // 参数列表
        private List<ParameterInfo> parameters;

        // 返回值
        private ParameterInfo returnType;
    }

    // 参数信息
    @Getter
    @Setter
    public static class ParameterInfo {
        // 名称
        private String name;

        // 类型
        private Class<?> type;

        // 描述
        private String description;
    }
}
