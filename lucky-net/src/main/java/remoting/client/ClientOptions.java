package remoting.client;

import rpc.options.RpcClientOptions;

/**
 * @Author:chaoqiang.zhou
 * @Description:客户端配置参数信息
 * @Date:Create in 15:25 2017/6/15
 */
public class ClientOptions {


    private String address;
    private int port;

    private int workThreads;
    //超时时间
    private int connectTimeOut = 10 * 1000;
    private int acquireTime = 10 * 1000;
    private int readTime = 30 * 1000;
    private int writeTime = 30 * 1000;
    private int maxConnections = 100;
    private int maxPendingAcquires = 100;
    private int receiveBufferSize = 1024 * 64;
    private int sendBufferSize = 1024 * 64;
    private int keepAliveTime = 30 * 60;  // 单位秒, 默认 30 分钟


    public ClientOptions(RpcClientOptions options) {
        this.address=options.getAddress();
        this.port=options.getPort();
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWorkThreads() {
        return workThreads;
    }

    public void setWorkThreads(int workThreads) {
        this.workThreads = workThreads;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public int getAcquireTime() {
        return acquireTime;
    }

    public void setAcquireTime(int acquireTime) {
        this.acquireTime = acquireTime;
    }

    public int getReadTime() {
        return readTime;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }

    public int getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(int writeTime) {
        this.writeTime = writeTime;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getMaxPendingAcquires() {
        return maxPendingAcquires;
    }

    public void setMaxPendingAcquires(int maxPendingAcquires) {
        this.maxPendingAcquires = maxPendingAcquires;
    }

    public int getReceiveBufferSize() {
        return receiveBufferSize;
    }

    public void setReceiveBufferSize(int receiveBufferSize) {
        this.receiveBufferSize = receiveBufferSize;
    }

    public int getSendBufferSize() {
        return sendBufferSize;
    }

    public void setSendBufferSize(int sendBufferSize) {
        this.sendBufferSize = sendBufferSize;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }
}
