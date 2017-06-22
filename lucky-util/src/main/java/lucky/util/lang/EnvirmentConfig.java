package lucky.util.lang;

/**
 * @Author:chaoqiang.zhou
 * @Description:环境变量的工具类信息
 * @Date:Create in 10:50 2017/6/22
 */
public class EnvirmentConfig {

    public static String getProperty(String key) {
        return System.getenv(key);
    }
}
