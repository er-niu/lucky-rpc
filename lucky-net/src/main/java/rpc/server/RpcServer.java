package rpc.server;

import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import remoting.server.RemotingServer;
import remoting.server.RemotingServerConfig;
import remoting.server.RemotingServerImpl;
import rpc.options.RpcServerOptions;

/**
 * @Author:chaoqiang.zhou
 * @Description:rpcserver端
 * @Date:Create in 10:36 2017/6/16
 */
public class RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
    private RpcServerOptions serverOptions;
    private RemotingServer server;

    public RpcServer(RpcServerOptions options) {
        this.serverOptions = serverOptions;
        this.server = createServer(options);
    }


    //服务启动得时候，进行注册服务列表信息
    public void registerServer(Object instance) {
        //服务端信息，注册到服务列表中去：包括对外暴漏得接口信息
        server.registerService(instance);
    }


    public void registerService(Class<?> clazz, Object instance) {
        server.registerService(clazz, instance);
    }


    public void start() {
        //服务端口启动
        this.server.start();
        if (serverOptions.isRegister()) {
            if (StringUtils.isEmpty(serverOptions.getAddress())) {
                return;
            }
        }
        //todo:注册到配置中心
    }

    private RemotingServer createServer(RpcServerOptions serverOptions) {
        return new RemotingServerImpl(new RemotingServerConfig(serverOptions));
    }


}
