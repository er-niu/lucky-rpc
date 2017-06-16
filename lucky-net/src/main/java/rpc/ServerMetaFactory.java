package rpc;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:06 2017/6/16
 */
public class ServerMetaFactory {

    //用class做为可以，可以一个类得对象多次使用
    private static final Map<Class<?>, ServerMeta> metaMap = new HashMap<>();

    private ServerMetaFactory() {
        //单例得工厂
    }

    public static synchronized ServerMeta get(Class<?> clazz) {
        ServerMeta meta = metaMap.get(clazz);
        if (meta == null) {
            meta = new ServerMeta(clazz);
            metaMap.put(clazz, meta);
        }
        return meta;
    }
}

