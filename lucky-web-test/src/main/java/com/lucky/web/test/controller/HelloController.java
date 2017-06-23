package com.lucky.web.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("test")
public class HelloController {

    @RequestMapping(value = "hello")
    public ModelAndView hello(ModelMap modelMap) {
        //底层是以view为根目录
        ModelAndView modelAndView = new ModelAndView("/hello/hello");
        System.out.println("测试信息");
        modelAndView.addObject("test", "lala");

        return modelAndView;
    }
}