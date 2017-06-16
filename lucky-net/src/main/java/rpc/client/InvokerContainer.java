package rpc.client;

import exception.RpcException;
import lombok.Getter;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import rpc.Invoker;
import rpc.InvokerFactory;
import rpc.Provider;
import rpc.options.RpcClientOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:chaoqiang.zhou
 * @Description:invoker得执行容器信息
 * @Date:Create in 11:34 2017/6/16
 */
public class InvokerContainer {

    private static final Logger logger = LoggerFactory.getLogger(InvokerContainer.class);
    //服务得注册容器，需要定时更新下线,
    private static final ConcurrentHashMap<String, InvokerContainer> container = new ConcurrentHashMap<>();
    private String server;
    //初始化得时候，进行执行容器初始化
    private List<Invoker> invokers;
    private boolean initialized;
    //这里是直连的信息，如果没有的话，默认是从配置中心获取
    //直连的操作方便进行本地调试操作
    @Getter
    private RpcClientOptions options;

    private InvokerContainer(String server) {
        this.server = server;
        //todo:回头考虑全配置信息,回头在写这块
        this.options = null;
    }


    public static InvokerContainer get(String server) {
        return container.computeIfAbsent(server, InvokerContainer::new);
    }


    //用得时候在初始化操作
    public List<Invoker> getInvoker() {
        if (!this.initialized) {
            synchronized (this) {
                if (!this.initialized) {
                    this.initialize();
                }
            }
        }

        return invokers;
    }


    private void initialize() {
        List<Invoker> list = initializeFromRegistry();
        //这块有点绕弯
        if (list.isEmpty()) {
            if (options == null) {
                logger.error("尝试直连、但没有找到对应得服务配置{}", server);
                throw new RpcException(RpcException.CLIENT_NO_PROVIDER, server);
            } else {
                logger.info("尝试直连服务{}", server);
                Invoker invoker = createInvoker(options);
                list.add(invoker);
            }
        }
        this.invokers = list;
        this.initialized = true;
    }


    //todo:从注册中心获取节点信息
    private List<Invoker> initializeFromRegistry() {
        List<Invoker> list = new ArrayList<>();
        //优先考虑直连
        if (options == null || options.isDiscovery()) {

        }


        return list;
    }


    //上游传过来得是provider,从注册中心获取配置
    private static RpcClientOptions getOptions(Provider provider, RpcClientOptions options) {
        RpcClientOptions clientOptions = new RpcClientOptions(provider);
        return clientOptions;
    }

    //获取对应得执行器操作，本地直连配置信息
    private static Invoker createInvoker(RpcClientOptions options) {
        return InvokerFactory.get(options);
    }
}
