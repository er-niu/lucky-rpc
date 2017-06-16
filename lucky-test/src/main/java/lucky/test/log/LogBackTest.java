package lucky.test.log;


import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:40 2017/5/17
 */

public class LogBackTest {
    static {
        System.setProperty("com.lucky.logger", "slf4j");
    }


    //todo:还是得用相应的logger信息
    public static Logger logger = LoggerFactory.getLogger(LogBackTest.class);
//    public static org.slf4j.impl.StaticLoggerBinder defaultLoggerContext = org.slf4j.impl.StaticLoggerBinder.getSingleton();
//

//    public static ch.qos.logback.classic.Logger logger2 = new ch.qos.logback.classic.LoggerContext().getLogger(LogBackTest.class);

    public static void main(String[] args) {

        logger.info("logback日志类型测试信息");
        logger.info("{},sdff,{}","sadf","哈哈");
        logger.error("logback日志类型输出文件");
        logger.trace("{},sdff,{}","sadf","哈哈");
        logger.debug("{},sdff,{}","sadf","哈哈");
        logger.error("{},sdff,{}","sadf","哈哈");

    }

    /***
     * This warning, i.e. not an error, message is reported when no SLF4J providers could be found on the class path. Placing one (and only one) of slf4j-nop.jar slf4j-simple.jar, slf4j-log4j12.jar, slf4j-jdk14.jar or logback-classic.jar on the class path should solve the problem. Note that these providers must target slf4j-api 1.8 or later.
     */
}
