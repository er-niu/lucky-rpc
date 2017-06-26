package com.lucky.db.exception;

/**
 * @Author:chaoqiang.zhou
 * @Description:所有异常的父类，但废弃了
 * @Date:Create in 10:17 2017/6/26
 */
@Deprecated
public class IDBException extends RuntimeException{
    private static final long serialVersionUID = 3880206998166270511L;

    public IDBException() {
        super();
    }

    public IDBException(String message) {
        super(message);
    }

    public IDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public IDBException(Throwable cause) {
        super(cause);
    }

}
