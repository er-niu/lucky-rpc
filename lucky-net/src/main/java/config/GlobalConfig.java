package config;

import com.sun.jndi.toolkit.url.UrlUtil;
import lombok.Getter;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:全局配置文件信息,后期需要把该配置文件，放置到一个特定得地方，比如window防止在C盘，linux放在在/home下面，这样就方便多了，更高一层次的抽象
 * @Date:Create in 16:45 2017/5/18
 */
public class GlobalConfig {

    public final static Logger logger = LoggerFactory.getLogger(GlobalConfig.class);
    //服务ip地址
    public final static String rpcServerIp = "rpc.register.ip";
    //注册中心地址
    public final static String rpcRegisterAddr = "rpc.register.addr";
    //全局配置文件得信息，加载到该全局得map中
    @Getter
    public final static Map<String, String> globalSetting = new HashMap<>();

    /**
     * 服务注册的ip地址,优先级最高，会自动覆盖，防止注册中心都是本地的注册信息
     */

    static {
        /**
         * /etd环境下面得配置信息，初始化global.conf文件中的配置信息
         */
        /**
         * 配置文件得格式
         * <config>
         *      <setting name='' key=''/>
         * </config>
         */
        try {
            URL etcUrl = Thread.currentThread().getContextClassLoader().getResource("config");
            String configPath = UrlUtil.decode(etcUrl.getPath()) + "/" + "global.conf";
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(configPath);
            Document document = db.parse(file);
            NodeList configList = document.getElementsByTagName("setting");
            //循环遍历节点信息，写入全局配置信息
            for (int i = 0; i < configList.getLength(); i++) {
                Element elem = (Element) configList.item(i);
                String value = elem.getAttribute("value");
                String key = elem.getAttribute("name");
                //防止全局变量
                globalSetting.put(key, value);
            }
            //解析该文件信息
        } catch (MalformedURLException e) {
            logger.error("init global.conf failed,the error{}", e);
        } catch (Exception e) {
            logger.error("init global.conf failed,the error{}", e);

        }

    }

}
