package rpc;

import lucky.util.boot.Application;
import lucky.util.lang.Classes;
import org.apache.commons.lang3.StringUtils;
import rpc.options.RpcServerOptions;
import rpc.server.RpcServer;

import java.util.List;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 19:45 2017/6/19
 */
public class RpcApplication extends Application {

    private RpcServer server;

    /**
     * 启动 RPC 服务
     *
     * @param bootClass 应用程序启动类
     * @param args      启动参数
     */
    public RpcApplication(Class<?> bootClass, String[] args) {
        super(bootClass, args);
        //todo:
        server = new RpcServer();
    }


    /**
     * 启动 RPC 服务
     *
     * @param bootClass      应用程序启动类
     * @param args           启动参数
     * @param defaultOptions 默认参数, 可在配置文件中进行覆盖
     */
    public RpcApplication(Class<?> bootClass, String[] args, RpcServerOptions defaultOptions) {
        super(bootClass, args);
        if (defaultOptions == null) {
            server = new RpcServer();
        } else {
            server = new RpcServer(defaultOptions);
        }
    }

    /**
     * 注册服务
     *
     * @param clazz    服务接口
     * @param instance 服务实例
     */
    public void registerService(Class<?> clazz, Object instance) {
        server.registerService(clazz, instance);
    }

    @Override
    protected void initDefaultProperties(Map<String, Object> props) {
        super.initDefaultProperties(props);
        props.put("spring.main.web_environment", false);
    }

    private void scanServices() {
        //todo:注解掉
//        scanServiceClass();
        scanServicePackage();
    }

    // 按类扫描, 支持扫描接口类和实现类，扫描包里面得类，进行注册：包括类和jar包里面的接口信息
    private void scanServiceClass() {
        String serviceClass = server.getServerOptions().getConfig().get("ServiceClass");
        if (StringUtils.isBlank(serviceClass)) {
            return;
        }

        String[] classes = serviceClass.split(",");
        for (String className : classes) {
            try {
                Class clazz = Class.forName(className);
                Object instance = ctx.getBean(clazz);
                if (clazz.isInterface()) {
                    server.registerService(clazz, instance);
                } else {
                    server.registerServer(instance);
                }
            } catch (Exception e) {
                logger.error("load class [{}] failed: {}", className, e);
            }
        }
    }

    //注册到本地得类仓库里面，本地的对外暴漏得接口得信息
    private void tryRegisterService(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if (clazz.isInterface()) {
                Object instance = getBean(clazz);
                if (instance == null) {
                    logger.warn("cannot find bean [{}]", className);
                } else {
                    this.server.registerService(clazz, instance);
                }
            }
        } catch (Exception e) {
            logger.error("load class [{}] failed: {}", className, e);
        }
    }

    // 按包扫描, 只扫描接口类,扫描指定的包
    private void scanServicePackage() {
        String[] packages;
        String servicePackage = server.getServerOptions().getConfig().get("ServicePackage");
        if (StringUtils.isBlank(servicePackage)) {
            packages = new String[]{bootClass.getPackage().getName() + ".iface"};
        } else {
            packages = servicePackage.split(",");
        }

        for (String pkg : packages) {
            List<String> classes = Classes.getClassListByPackage(pkg, false);
            classes.forEach(this::tryRegisterService);
        }
    }

    @Override
    protected void load() {
        try {
            scanServices();
            server.start();
        }catch (Exception e){
            logger.info("the server start failed,error{}",e);
            //非正常退出程序
            System.exit(1);
        }

    }
}
