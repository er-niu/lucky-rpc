package rpc.client;

import exception.RpcException;
import lombok.Getter;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import registry.CuratorRegistry;
import registry.Registry;
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
    //服务得注册容器，需要定时更新下线,通过watcher机制，进行动态更新即可
    private static final ConcurrentHashMap<String, InvokerContainer> containers = new ConcurrentHashMap<>();
    private String server;
    //初始化得时候，进行执行容器初始化
    private List<Invoker> invokers;
    private boolean initialized;
    private static final Registry registry = CuratorRegistry.registry;

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
        return containers.computeIfAbsent(server, InvokerContainer::new);
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
        List<Invoker> list = new ArrayList<>();
        //优先考虑直连操作,options有配置，isDiscovery=false
        if (options != null) {
            Invoker invoker = createInvoker(options);
            list.add(invoker);
        } else {
            //从配置中心进行获取
            list = initializeFromRegistry();
        }

        if (list.isEmpty()) {
            logger.error("没有找到对应得服务配置{}", server);
            throw new RpcException(RpcException.CLIENT_NO_PROVIDER, server);
        }
        this.invokers = list;
        this.initialized = true;
    }


    //todo:从注册中心获取节点信息
    private List<Invoker> initializeFromRegistry() {
        List<Invoker> list = new ArrayList<>();
        //优先考虑直连
        if (options == null || !options.isDiscovery()) {
            //这边加一个watcher的机制
            List<Provider> providers = registry.lookup(this.server, InvokerContainer::updateProviders);
            if (!providers.isEmpty()) {
                providers.forEach(provider -> {
                    Invoker invoker = createInvoker(getOptions(provider, options));
                    list.add(invoker);
                });
                //添加一个watcher机制信息
            } else {
                logger.warn("从注册中心未获取到任务属于服务{}的节点", server);
            }
        }
        //从注册中心get操作即可,从zk上获取即可
        return list;
    }


    //动态更新维护得列表信息
    public static void updateProviders(String server, List<Provider> providers) {
        List<Invoker> invokers = new ArrayList<>();
        providers.forEach(provider -> {
            Invoker invoker = createInvoker(getOptions(provider, null));
            invokers.add(invoker);
        });

        InvokerContainer container = containers.get(server);
        logger.info("后台服务{}变更，更新节点列表信息，节点信息{}", server, providers.toString());
        container.setInvokers(invokers);

    }

    private void setInvokers(List<Invoker> invokers) {
        this.invokers = invokers;
    }

    //上游传过来得是provider,从注册中心获取配置
    private static RpcClientOptions getOptions(Provider provider, RpcClientOptions options) {
        RpcClientOptions clientOptions = new RpcClientOptions(provider);
        if (options != null) {
            clientOptions.setVersion(options.getVersion());
            clientOptions.getConfig().putAll(options.getConfig());
        }
        return clientOptions;
    }

    //获取对应得执行器操作，本地直连配置信息
    private static Invoker createInvoker(RpcClientOptions options) {
        return InvokerFactory.get(options);
    }
}
