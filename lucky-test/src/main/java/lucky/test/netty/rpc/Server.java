package lucky.test.netty.rpc;

import remoting.server.RemotingServerConfig;
import remoting.server.RemotingServerImpl;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:45 2017/6/20
 */
public class Server {
    public static void main(String[] args) {
        System.setProperty("com.lucky.logger", "log4j");

        RemotingServerConfig  serverConfig= new RemotingServerConfig();
        serverConfig.setAddress("192.168.9.196");
        serverConfig.setPort(8865);
        RemotingServerImpl remotingServer = new RemotingServerImpl(serverConfig);
        remotingServer.start();
    }

}
