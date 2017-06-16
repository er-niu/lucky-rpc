package remoting.server;

import remoting.data.NettyRequest;
import remoting.data.NettyResponse;
import exception.RpcException;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author:chaoqiang.zhou
 * @Description:业务端处处理器操作
 * @Date:Create in 16:20 2017/6/14
 */
public class LocalNettyExecutor implements NettyRequestProcessor {

    //从容器中获取执行器操作
    private final ServerContainer serverContainer;

    public LocalNettyExecutor(ServerContainer container) {
        this.serverContainer = container;
    }

    @Override
    public NettyResponse processRequest(ChannelHandlerContext ctx, NettyRequest request) {
        ServerContainer.MethodExecutor methodExecutor = serverContainer.getExecutor(request.getServiceName(), request.getMethodName());
        if (methodExecutor == null) {
            throw new RpcException(RpcException.BIZ_EXCEPTION);
        }
        Object result = methodExecutor.invoke(request.getArguments());
        NettyResponse response = NettyResponse.success(result);
        return response;
    }
}
