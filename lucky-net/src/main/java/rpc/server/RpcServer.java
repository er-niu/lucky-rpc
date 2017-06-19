package rpc.server;

import exception.RpcException;
import lombok.Getter;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import registry.CuratorRegistry;
import registry.Registry;
import remoting.server.RemotingServer;
import remoting.server.RemotingServerConfig;
import remoting.server.RemotingServerImpl;
import rpc.Provider;
import rpc.options.RpcServerOptions;

/**
 * @Author:chaoqiang.zhou
 * @Description:rpcserver端
 * @Date:Create in 10:36 2017/6/16
 */
public class RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
    @Getter
    private RpcServerOptions serverOptions;
    private RemotingServer server;
    private static final Registry registry = CuratorRegistry.registry;


    public RpcServer() {
        //从配置文件中读取配置信息
    }

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
        try {
            //服务端口启动
            this.server.start();
            //判断是否注册到配置中心去
            if (serverOptions.isRegister()) {
                if (StringUtils.isEmpty(serverOptions.getAddress())) {
                    return;
                }

                //把provider注册到配置中心去
                registry.register(() -> {
                    Provider provider = new Provider();
                    provider.setAddress(serverOptions.getAddress());
                    provider.setName(serverOptions.getName());
                    provider.setDescription(serverOptions.getDescription());
                    provider.setPort(serverOptions.getPort());
                    provider.setVersion(serverOptions.getVersion());
                    provider.getSettings().putAll(serverOptions.getConfig());
                    return provider;
                });
            }

        } catch (Exception e) {
            logger.error("sever{},start failed,error{}", serverOptions.getName(), e);
            throw new RpcException("server start failed");
        }

    }

    private RemotingServer createServer(RpcServerOptions serverOptions) {
        return new RemotingServerImpl(new RemotingServerConfig(serverOptions));
    }


}
