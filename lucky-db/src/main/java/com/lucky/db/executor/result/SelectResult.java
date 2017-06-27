package com.lucky.db.executor.result;

import java.util.List;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:查询返回的结果集信息 在JDK7中只要实现了AutoCloseable或Closeable接口的类或接口，都可以使用try-with-resource来实现异常处理和资源关闭
 * @Date:Create in 9:24 2017/6/27
 */
public class SelectResult implements AutoCloseable {


    //返回某个类操作
    public <T> T one(Class<T> clazz) {

        return null;
    }


    //返回集合操作
    public <T> List<T> all(Class<T> clazz) {

        return null;
    }


    //读取记录得第一行操作
    public Map<String, Object> one() {

        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
