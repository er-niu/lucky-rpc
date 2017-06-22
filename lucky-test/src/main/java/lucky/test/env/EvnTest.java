package lucky.test.env;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @Author:chaoqiang.zhou
 * @Description:获取环境变量的信息
 * @Date:Create in 10:48 2017/6/22
 */
public class EvnTest {

    public static void main(String[] args) {
        System.out.println(System.getenv("CLASSPATH"));
        System.out.println(System.getProperty("CLASSPATH"));
        Map<String, String> map = System.getenv();
        System.out.println(map.get("CLASSPATH"));
        for(Iterator<String> itr = map.keySet().iterator(); itr.hasNext();){
            String key = itr.next();
            System.out.println(key + "=" + map.get(key));
        }
    }
}
