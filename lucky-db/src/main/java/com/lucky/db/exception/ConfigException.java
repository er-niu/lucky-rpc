package com.lucky.db.exception;

/**
 * @Author:chaoqiang.zhou
 * @Description:数据源配置的异常信息，继承了，PersistenceException的异常信息
 * @Date:Create in 17:56 2017/6/24
 */
public class ConfigException extends PersistenceException {
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


}
