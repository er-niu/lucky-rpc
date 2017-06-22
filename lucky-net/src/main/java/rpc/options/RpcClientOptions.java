package rpc.options;

import lombok.Getter;
import lombok.Setter;
import rpc.Provider;

import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 13:59 2017/6/16
 */
@Getter
@Setter
public class RpcClientOptions {

    private String name;
    private String group;
    private String version;
    private boolean discovery = true;
    private String address;
    private int port;
    private Map<String, String> config;


    public RpcClientOptions(Provider provider) {
        this.name = provider.getName();
        this.address = provider.getAddress();
        this.config = config;
        this.port = provider.getPort();
        this.version = provider.getVersion();
    }
}
