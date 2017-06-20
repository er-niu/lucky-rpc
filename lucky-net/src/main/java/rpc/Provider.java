package rpc;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:服务元信息
 * @Date:Create in 13:49 2017/6/16
 */
@Getter
@Setter
public class Provider {

    private String name;
    private String address;
    private int port;
    private String version;
    private String description;
    private Map<String, String> settings=new HashMap<>();

    //连接数
    private int clients;
}
