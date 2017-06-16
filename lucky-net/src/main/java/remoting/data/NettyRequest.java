package remoting.data;

/**
 * @Author:chaoqiang.zhou
 * @Description:netty端的请求参数
 * @Date:Create in 11:16 2017/6/12
 */
public class NettyRequest {


    //报文的tocken，防止恶意的攻击
    private String token = "lucky.request";
    private String serviceName;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] arguments;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }
}
