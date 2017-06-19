package lucky.util.zk;

/**
 * @Author:chaoqiang.zhou
 * @Description:curator工厂方法
 * @Date:Create in 13:43 2017/6/19
 */
public class CuratorFactory {

    public static CuratorClient client = null;

    private CuratorFactory() {
        //单例
    }

    public static synchronized CuratorClient get(String nameSpace, String address) {
        //初始化client信息
        if (client == null) {
            client = new CuratorClient(nameSpace, address);
        }
        return client;
    }
}
