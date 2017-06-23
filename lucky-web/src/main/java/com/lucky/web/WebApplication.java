package com.lucky.web;

import lucky.util.boot.Application;

import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:参考http://www.cnblogs.com/nicekk/p/6059446.html，进行初始化页面配置信息
 * @Date:Create in 15:15 2017/6/23
 */
public class WebApplication extends Application {
    public WebApplication(Class<?> bootClass, String[] args) {
        super(bootClass, args);
    }

    @Override
    protected void initDefaultProperties(Map<String, Object> props) {
        super.initDefaultProperties(props);
        props.put("spring.velocity.content-type", "text/html");
        props.put("spring.velocity.prefix", "/");
        props.put("spring.velocity.suffix", ".vm");
        //默认查找文件得信息
        props.put("spring.velocity.resource-loader-path", "classpath:/view");
        props.put("spring.velocity.request-context-attribute", "rc");
        props.put("spring.velocity.properties.layout-url", "layout/default.vm");
        props.put("spring.velocity.properties.input.encoding", "UTF-8");
        props.put("spring.velocity.properties.output.encoding", "UTF-8");
    }
}
