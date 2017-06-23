package lucky.util.log;

import lucky.util.log.log4j.Log4jLoggerAdapter;
import lucky.util.log.slf4j.Slf4jLoggerAdapter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:chaoqiang.zhou
 * @Description:log的日志工厂用来获取相对应的logger信息
 * @Date:Create in 14:59 2017/5/17
 */
public class LoggerFactory {


    private LoggerFactory() {
        //防止实例化
    }

    private static volatile LoggerAdapter LOGGER_ADAPTER;

    private static final ConcurrentHashMap<String, Logger> LOGGERS = new ConcurrentHashMap<>();

    //查询常用的日志框架信息
    static {
        //todo:该配置需要提前初始化
        String logger = System.getProperty("com.lucky.logger");
        if(StringUtils.isEmpty(logger)){
            //默认是log4j的配置信息
            setLoggerAdapter(new Log4jLoggerAdapter());
        }else if(logger.equals(LogType.SLF4J.displayName()) || logger.equals(LogType.LOGBACK.displayName())){
            setLoggerAdapter(new Slf4jLoggerAdapter());

        }
    }


    /**
     * 设置日志输出器供给器
     *
     * @param loggerAdapter 日志输出器供给器
     */
    public static void setLoggerAdapter(LoggerAdapter loggerAdapter) {
        if (loggerAdapter != null) {
            Logger logger = loggerAdapter.getLogger(LoggerFactory.class.getName());
            logger.info("using logger: " + loggerAdapter.getClass().getName());
            LoggerFactory.LOGGER_ADAPTER = loggerAdapter;
            //清理logger缓存的信息
            for (String key : LOGGERS.keySet()) {
                LOGGERS.put(key, LOGGER_ADAPTER.getLogger(key));
            }
        }
    }

    /**
     * 获取日志输出器操作
     *
     * @param key
     * @return
     */
    public static Logger getLogger(Class<?> key) {
        Logger logger = LOGGERS.get(key.getName());
        if (logger == null) {
            LOGGERS.putIfAbsent(key.getName(), LOGGER_ADAPTER.getLogger(key.getName()));
            logger = LOGGERS.get(key.getName());

        }
        return logger;
    }

    /**
     * 获取日志输出器操作
     *
     * @param key
     * @return
     */
    public static Logger getLogger(String key) {
        Logger logger = LOGGERS.get(key);
        if (logger == null) {
            LOGGERS.putIfAbsent(key, LOGGER_ADAPTER.getLogger(key));
            logger = LOGGERS.get(key);

        }
        return logger;
    }


    /**
     * 动态设置输出日志级别
     *
     * @param level 日志级别
     */
    public static void setLevel(Level level) {
        LOGGER_ADAPTER.setLevel(level);
    }

    /**
     * 获取日志级别
     *
     * @return 日志级别
     */
    public static Level getLevel() {
        return LOGGER_ADAPTER.getLevel();
    }

    /**
     * 获取日志文件
     *
     * @return 日志文件
     */
    public static File getFile() {
        return LOGGER_ADAPTER.getFile();
    }

}
