package com.lucky.web.test;

import com.lucky.web.WebApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:19 2017/6/20
 */

@SpringBootApplication
public class WebBootStrap {


    /**
     * 主函数
     *
     * @param args
     */
    public static void main(String[] args) {
        WebApplication application = new WebApplication(WebBootStrap.class, args);
        application.run();


    }
}
