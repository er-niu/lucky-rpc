package rpc;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author:chaoqiang.zhou
 * @Description:服务元信息
 * @Date:Create in 13:49 2017/6/16
 */
@Getter
@Setter
public class Provider {

    private String name;
    private String type;
    private String address;
    private int port;
    private String version;
    private String description;
    private String machine;
    //连接数
    private int clients;
}
