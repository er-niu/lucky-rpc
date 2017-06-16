package lucky.test.log;

import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;

/**
 * @Author:chaoqiang.zhou
 * @Description:log4j日志测试信息
 * @Date:Create in 18:28 2017/5/17
 */
public class Log4jTest {
    static{
        System.setProperty("com.lucky.logger","log4j");
    }
    public static Logger logger= LoggerFactory.getLogger(Log4jTest.class);

    public static void main(String[] args) {

        logger.info("log4j日志类型测试信息{}","asdf");
    }
}
