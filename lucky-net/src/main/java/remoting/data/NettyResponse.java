package remoting.data;

import exception.RpcException;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:45 2017/6/12
 */
public class NettyResponse {


    private boolean success;

    private String errorInfo;

    private long serverTime;

    private int errorCode;

    private Object result;


    public static NettyResponse success(Object result) {
        NettyResponse response = new NettyResponse();
        response.setSuccess(true);
        response.setServerTime(System.currentTimeMillis());
        response.setResult(result);
        return response;
    }


    public static NettyResponse failed(Throwable e) {
        NettyResponse response = new NettyResponse();
        response.setServerTime(System.currentTimeMillis());
        response.setSuccess(false);
        if (e instanceof RpcException) {
            response.setErrorCode(((RpcException) e).getCode());
        } else {
            response.setErrorCode(RpcException.UNKNOWN_EXCEPTION);
        }

        return response;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

}
