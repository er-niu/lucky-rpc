package com.lucky.db.exception;

/**
 * @Author:chaoqiang.zhou
 * @Description: * 持久化异常
 * 可以看到这个类只是继承了一个废弃的IDBException，其他都一样
 * @Date:Create in 10:18 2017/6/26
 */
public class PersistenceException extends IDBException {
    private static final long serialVersionUID = -7537395265357977271L;

    public PersistenceException() {
        super();
    }

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }
}
