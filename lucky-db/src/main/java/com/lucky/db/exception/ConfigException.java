package com.lucky.db.exception;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:56 2017/6/24
 */
public class ConfigException extends RuntimeException {
    public ConfigException() {
        super();
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }

    protected ConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
