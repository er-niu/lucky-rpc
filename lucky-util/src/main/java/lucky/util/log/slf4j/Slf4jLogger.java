package lucky.util.log.slf4j;


import lucky.util.log.Logger;

import java.io.Serializable;


/***
 * 支持logback打印
 * 底层已经引入了logback的jar包，直接加入相应的配置即可
 * 详情看lucky-test里面的logtest相关信息
 */
public class Slf4jLogger implements Logger, Serializable {

    private static final long serialVersionUID = 1L;

    private final org.slf4j.Logger logger;

    public Slf4jLogger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    public void trace(String msg) {
        logger.trace(msg);
    }

    public void trace(Throwable e) {
        logger.trace(e.getMessage(), e);
    }

    public void trace(String msg, Throwable e) {
        logger.trace(msg, e);
    }

    @Override
    public void trace(String format, Object arg) {
        this.logger.trace(format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        this.logger.trace(format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object... args) {
        this.logger.trace(format, args);
    }

    public void debug(String msg) {
        logger.debug(msg);
    }

    public void debug(Throwable e) {
        logger.debug(e.getMessage(), e);
    }

    public void debug(String msg, Throwable e) {
        logger.debug(msg, e);
    }

    @Override
    public void debug(String format, Object arg) {
        this.logger.debug(format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        this.logger.debug(format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... args) {
        this.logger.debug(format, args);
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void info(Throwable e) {
        logger.info(e.getMessage(), e);
    }

    public void info(String msg, Throwable e) {
        logger.info(msg, e);
    }

    @Override
    public void info(String format, Object arg) {
        this.logger.info(format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        this.logger.info(format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... args) {
        this.logger.info(format, args);
    }

    public void warn(String msg) {
        logger.warn(msg);
    }

    public void warn(Throwable e) {
        logger.warn(e.getMessage(), e);
    }

    public void warn(String msg, Throwable e) {
        logger.warn(msg, e);
    }

    @Override
    public void warn(String format, Object arg) {
        this.logger.warn(format, arg);
    }

    @Override
    public void warn(String format, Object... args) {
        this.logger.warn(format, args);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        this.logger.warn(format, arg1, arg2);
    }

    public void error(String msg) {
        logger.error(msg);
    }

    public void error(Throwable e) {
        logger.error(e.getMessage(), e);
    }

    public void error(String msg, Throwable e) {
        logger.error(msg, e);
    }

    @Override
    public void error(String format, Object arg) {
        this.logger.error(format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        this.logger.error(format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... args) {
        this.logger.error(format, args);
    }

    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

}
