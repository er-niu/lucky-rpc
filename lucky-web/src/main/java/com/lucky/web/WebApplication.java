package com.lucky.web;

import lucky.util.boot.Application;
import lucky.util.lang.StringConverter;

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
//        props.put("spring.velocity.prefix", "/");
//        props.put("spring.velocity.suffix", ".vm");
//        =false

        //默认查找文件得信息
        props.put("spring.velocity.checkTemplateLocation", "false");
        //修改默认得路径信息
        props.put("spring.velocity.resource-loader-path", "classpath:/view/");
        props.put("spring.velocity.request-context-attribute", "rc");
        props.put("spring.velocity.properties.layout-url", "layout/default.vm");
        props.put("spring.velocity.properties.input.encoding", "UTF-8");
        props.put("spring.velocity.properties.output.encoding", "UTF-8");
        //可以在这里扩展标签，具体请参考http://blog.csdn.net/zhangdaiscott/article/details/21477533
        //增加js得版本号
        /**
         * 1.方便版本控制，比如1.1版本的样式表，可以升级为2.0版本的样式表
         * 2.强制浏览器更新(因为http请求时，如果访问的路径不变，而客户端缓存中又有该文件时，浏览器会直接调用缓存中的文件，这样的话，即使服务端的css内容变化了，但是客户端仍然有可能显示的是旧文件，而加上新的版本号以后，浏览器会认为这是一个新的访问地址，会重新下载最新版本的文件)
         *参考：http://blog.csdn.net/zhangdaiscott/article/details/21477533，自定义标签
         */
        String[] directives = new String[]{
                "com.lucky.web.directive.JsDirective",
                "com.lucky.web.directive.CssDirective"
        };
        props.put("spring.velocity.properties.userdirective", StringConverter.toString(",", directives));

    }
}
