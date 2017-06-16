package rpc.options;

import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:暴漏的服务端元信息
 * @Date:Create in 10:37 2017/6/16
 */
public class RpcServerOptions {


    private String name;

    private String version;
    //是否注册到配置中心
    private boolean register=true;

    private String address;
    private int port;

    private String description;

    //其他得配置信息
    private Map<String,String> config;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
