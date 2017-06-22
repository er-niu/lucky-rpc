package rpc.options;

import com.sun.jndi.toolkit.url.UrlUtil;
import config.ActiveProfileConfig;
import exception.ConfigException;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:暴漏的服务端元信息
 * @Date:Create in 10:37 2017/6/16
 */
public class RpcServerOptions {

    public static final Map<String, RpcServerOptions> serverContainer = new HashMap<>();
    public static final Logger logger = LoggerFactory.getLogger(RpcServerOptions.class);

    static {
        try {
            URL etcUrl = Thread.currentThread().getContextClassLoader().getResource("config");
            String configPath = UrlUtil.decode(etcUrl.getPath()) + "/" + "remote.client.conf";
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(configPath);
            Document document = db.parse(file.getAbsolutePath());
            NodeList configList = document.getElementsByTagName("server");
            //循环遍历节点信息，写入全局配置信息
            for (int i = 0; i < configList.getLength(); i++) {
                Element elem = (Element) configList.item(i);
                RpcServerOptions rpcServerOptions = new RpcServerOptions();
                rpcServerOptions.setName(elem.getAttribute("name"));
                rpcServerOptions.setPort(Integer.valueOf(elem.getAttribute("port")));
                rpcServerOptions.setAddress(elem.getAttribute("address"));
                rpcServerOptions.setRegister(Boolean.valueOf(elem.getAttribute("register")));
                rpcServerOptions.setVersion(elem.getAttribute("version"));
                rpcServerOptions.setDescription(elem.getAttribute("description"));


                //其他的配置信息
                NodeList childList = elem.getElementsByTagName("setting");
                for (int j = 0; j < childList.getLength(); j++) {
                    Element child = (Element) childList.item(j);
                    String value = child.getAttribute("value");
                    String key = child.getAttribute("name");
                    rpcServerOptions.getConfig().put(key, value);
                }
                serverContainer.put(rpcServerOptions.getName(), rpcServerOptions);
            }
        } catch (Exception e) {
            logger.error("初始化server配置信息出错，error{}", e);
        }


    }


    public static RpcServerOptions getServerOptions() {
        String serverName = (String) ActiveProfileConfig.configs.get("server.name");
        if (StringUtils.isEmpty(serverName)) {
            logger.error("未配置server.name属性信息");
            throw new ConfigException("未配置server.name信息");
        }
        return serverContainer.get(serverName);
    }

    private String name;
    private String version;
    //是否注册到配置中心
    private boolean register = true;
    private String address;
    private int port;

    private String description;

    //其他得配置信息
    private Map<String, String> config = new HashMap<>();


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
