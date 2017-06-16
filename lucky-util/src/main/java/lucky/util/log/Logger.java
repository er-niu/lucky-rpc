package lucky.util.log;

/**
 * @Author:chaoqiang.zhou
 * @Description:日志定义接口
 * @Date:Create in 15:03 2017/5/17
 */
public interface Logger {

    /**
     * 输出跟踪信息
     *
     * @param msg 信息内容
     */
    public void trace(String msg);
    /**
     * 输出跟踪信息
     *
     * @param e 异常信息
     */
    public void trace(Throwable e);

    /**
     * 输出跟踪信息
     *
     * @param msg 信息内容
     * @param e 异常信息
     */
    public void trace(String msg, Throwable e);


    void trace(String format, Object arg);

    void trace(String format, Object arg1, Object arg2);

    void trace(String format, Object... args);
    /**
     * 输出调试信息
     *
     * @param msg 信息内容
     */
    public void debug(String msg);

    /**
     * 输出调试信息
     *
     * @param e 异常信息
     */
    public void debug(Throwable e);

    /**
     * 输出调试信息
     *
     * @param msg 信息内容
     * @param e 异常信息
     */
    public void debug(String msg, Throwable e);

    void debug(String format, Object arg);

    void debug(String format, Object arg1, Object arg2);

    void debug(String format, Object... args);


    /**
     * 输出普通信息
     *
     * @param msg 信息内容
     */
    public void info(String msg);

    /**
     * 输出普通信息
     *
     * @param e 异常信息
     */
    public void info(Throwable e);

    /**
     * 输出普通信息
     *
     * @param msg 信息内容
     * @param e 异常信息
     */
    public void info(String msg, Throwable e);


    void info(String format, Object arg);

    void info(String format, Object arg1, Object arg2);

    void info(String format, Object... args);

    /**
     * 输出警告信息
     *
     * @param msg 信息内容
     */
    public void warn(String msg);

    /**
     * 输出警告信息
     *
     * @param e 异常信息
     */
    public void warn(Throwable e);

    /**
     * 输出警告信息
     *
     * @param msg 信息内容
     * @param e 异常信息
     */
    public void warn(String msg, Throwable e);


    void warn(String format, Object arg);

    void warn(String format, Object... args);

    void warn(String format, Object arg1, Object arg2);

    /**
     * 输出错误信息
     *
     * @param msg 信息内容
     */
    public void error(String msg);

    /**
     * 输出错误信息
     *
     * @param e 异常信息
     */
    public void error(Throwable e);

    /**
     * 输出错误信息
     *
     * @param msg 信息内容
     * @param e 异常信息
     */
    public void error(String msg, Throwable e);

    void error(String format, Object arg);

    void error(String format, Object arg1, Object arg2);

    void error(String format, Object... args);

    /**
     * 跟踪信息是否开启
     *
     * @return 是否开启
     */
    public boolean isTraceEnabled();

    /**
     * 调试信息是否开启
     *
     * @return 是否开启
     */
    public boolean isDebugEnabled();

    /**
     * 普通信息是否开启
     *
     * @return 是否开启
     */
    public boolean isInfoEnabled();

    /**
     * 警告信息是否开启
     *
     * @return 是否开启
     */
    public boolean isWarnEnabled();

    /**
     * 错误信息是否开启
     *
     * @return 是否开启
     */
    public boolean isErrorEnabled();
}
