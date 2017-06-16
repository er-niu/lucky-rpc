package remoting.server;

import rpc.options.RpcServerOptions;

/**
 * @Author:chaoqiang.zhou
 * @Description:server端配置信息 服务端配置参数，可以进行灵活得配置
 * @Date:Create in 15:53 2017/6/9
 */
public class RemotingServerConfig {


    //ip
    private String address;
    //端口号
    private int port;
    private int bossThread = Runtime.getRuntime().availableProcessors();
    private int workThread = Runtime.getRuntime().availableProcessors() * 2;
    /**
     * handler处理业务流程，免去了worker占用cpu的时间
     */
    private int workSelectorThread = Runtime.getRuntime().availableProcessors() * 4;
    private int minThread = 10;
    private int maxThread = 500;
    //客户端最大的连接数限制
    private int maxClients=1000;
    private int readTimeOut = 30 * 1000;
    private int writeTimeOut = 30 * 1000;
    /*接受缓冲区的大小设置，默认是64M*
     * 设置snd_buf
     * 一般对于要建立大量连接的应用, 不建议设置这个值, 因为linux内核对snd_buf的大小是动态调整的, 内核是很聪明的.
     *
     */
    private int receiveBufferSize = 1024 * 64;
    private int senBufferSize = 1024 * 64;
    private int keepAliveTime = 30 * 60;//单位是秒，默认是30分钟
    private boolean tcpNoDelay = true;
    //服务端处理线程全忙后，允许多少个新请求进入等待。
    private int backLogRequest = 1024;
    //是否是堆外内存
    private boolean serverPooledByteBufAllocatorEnable = false;


    public RemotingServerConfig() {

    }


    //
    public RemotingServerConfig(RpcServerOptions serverOptions){
        this.address=serverOptions.getAddress();
        this.port=serverOptions.getPort();
        //其他的配置后续可以从serveroptions得config中进行添加
    }

    public boolean isServerPooledByteBufAllocatorEnable() {
        return serverPooledByteBufAllocatorEnable;
    }

    public void setServerPooledByteBufAllocatorEnable(boolean serverPooledByteBufAllocatorEnable) {
        this.serverPooledByteBufAllocatorEnable = serverPooledByteBufAllocatorEnable;
    }

    public int getBackLogRequest() {
        return backLogRequest;
    }

    public void setBackLogRequest(int backLogRequest) {
        this.backLogRequest = backLogRequest;
    }

    public int getWorkSelectorThread() {
        return workSelectorThread;
    }

    public void setWorkSelectorThread(int workSelectorThread) {
        this.workSelectorThread = workSelectorThread;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBossThread() {
        return bossThread;
    }

    public void setBossThread(int bossThread) {
        this.bossThread = bossThread;
    }

    public int getWorkThread() {
        return workThread;
    }

    public void setWorkThread(int workThread) {
        this.workThread = workThread;
    }

    public int getMinThread() {
        return minThread;
    }

    public void setMinThread(int minThread) {
        this.minThread = minThread;
    }

    public int getMaxThread() {
        return maxThread;
    }

    public void setMaxThread(int maxThread) {
        this.maxThread = maxThread;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getWriteTimeOut() {
        return writeTimeOut;
    }

    public void setWriteTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }

    public int getReceiveBufferSize() {
        return receiveBufferSize;
    }

    public void setReceiveBufferSize(int receiveBufferSize) {
        this.receiveBufferSize = receiveBufferSize;
    }

    public int getSenBufferSize() {
        return senBufferSize;
    }

    public void setSenBufferSize(int senBufferSize) {
        this.senBufferSize = senBufferSize;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
