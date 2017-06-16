package config;

/**
 * @Author:chaoqiang.zhou
 * @Description:全局配置文件信息
 * @Date:Create in 16:45 2017/5/18
 */
public class GlobalConfig {


    /**
     * 服务注册的ip地址
     */
    private String rpcRegisterIp;
    /**
     * 服务注册的类型
     * zk？etcd？others？
     */
    private String rpcRegisterType;
    /**
     * 客户端读超时的时间
     */
    private int rpcClientReadTimeOut;
    /**
     * 服务上报类型
     */
    private String rpcClientStatsType;
    private String rpcClientStatsEnable;

    private String rpcServerStatsType;
    private String rpcServerStatsEnable;

    static{
        /**
         * 初始化global.conf文件中的配置信息
         */
    }

}
