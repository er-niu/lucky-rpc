package config;

import com.sun.jndi.toolkit.url.UrlUtil;
import lucky.util.lang.EnvirmentConfig;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @Author:chaoqiang.zhou
 * @Description:对外的配置，用来包含服务端的信息，通过指定激活得配置文件来加载默认的配置信息，为rpcserveroptions服务 * @Date:Create in 16:46 2017/5/18
 */
public class ActiveProfileConfig {

    public static final Properties configs = new Properties();
    public static final Logger logger = LoggerFactory.getLogger(ActiveProfileConfig.class);

    static {
        try {
            //project-stg.properties,默认的文件是project.properties
            //todo:解析有问题
            URL etcUrl = Thread.currentThread().getContextClassLoader().getResource("config");
            String profile = EnvirmentConfig.getProperty("default.profiles.active");
            String configPath = "";
            if (StringUtils.isNotEmpty(profile)) {
                //加载active.profile配置文件信息
                configPath = UrlUtil.decode(etcUrl.getPath()) + "/" + "project-" + profile + ".properties";
            } else {
                configPath = UrlUtil.decode(etcUrl.getPath()) + "/" + "project.properties";
            }
            InputStream in = new BufferedInputStream(new FileInputStream(new File(configPath)));
            configs.load(in);     ///加载属性列表
        } catch (Exception e) {
            logger.error("初始化激活配置文件信息出错，error{}", e);
        }

    }
}
