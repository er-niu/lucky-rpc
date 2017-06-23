package rpc.options;

import com.sun.jndi.toolkit.url.UrlUtil;
import lombok.Getter;
import lombok.Setter;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import rpc.Provider;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:直连操作初始化信息
 * @Date:Create in 13:59 2017/6/16
 */
@Getter
@Setter
public class RpcClientOptions {

    public static final Map<String, RpcClientOptions> clientContainers = new HashMap<>();
    public static final Logger logger = LoggerFactory.getLogger(RpcClientOptions.class);


    static {
        try {
            URL etcUrl = Thread.currentThread().getContextClassLoader().getResource("config");
            String configPath = UrlUtil.decode(etcUrl.getPath()) + "/" + "rpc.client.conf";
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(configPath);
            Document document = db.parse(file.getAbsolutePath());
            NodeList configList = document.getElementsByTagName("server");
            //循环遍历节点信息，写入全局配置信息
            for (int i = 0; i < configList.getLength(); i++) {
                Element elem = (Element) configList.item(i);
                RpcClientOptions rpcClientOptions = new RpcClientOptions();
                rpcClientOptions.setName(elem.getAttribute("name"));
                rpcClientOptions.setPort(Integer.valueOf(elem.getAttribute("port")));
                rpcClientOptions.setAddress(elem.getAttribute("address"));
                rpcClientOptions.setDiscovery(Boolean.valueOf(elem.getAttribute("discovery")));
                clientContainers.put(rpcClientOptions.getName(), rpcClientOptions);
            }
        } catch (Exception e) {
            logger.error("初始化server配置信息出错，error{}", e);
        }


    }

    private String name;
    private String group;
    private String version;
    private boolean discovery = true;
    private String address;
    private int port;
    private Map<String, String> config=new HashMap<>();


    public RpcClientOptions() {

    }

    public RpcClientOptions(Provider provider) {
        this.name = provider.getName();
        this.address = provider.getAddress();
        this.config = config;
        this.port = provider.getPort();
        this.version = provider.getVersion();
    }
}
