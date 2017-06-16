package remoting.server;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:07 2017/6/9
 */
public interface RemotingServer {

    public void start();
    public void stop();
    void registerService(Object instance);
    void registerService(Class<?> clazz, Object instance);
}
