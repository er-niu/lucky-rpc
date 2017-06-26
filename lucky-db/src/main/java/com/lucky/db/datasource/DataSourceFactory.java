package com.lucky.db.datasource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;

/**
 * @Author:chaoqiang.zhou
 * @Description:数据源的工厂
 * @Date:Create in 17:34 2017/6/24
 */
public class DataSourceFactory {

    //缓存需要的数据源信息，懒加载模式
    private HashMap<String, DataSource> dataSourceContainer = new HashMap<>();


    //模式的数据源模式
    public static DataSource get(String name) {
        Objects.requireNonNull(name);
        return null;
    }


    /**
     * 分库分表数据源配置
     *
     * @param name:数据源名称
     * @param key：hash的key信息
     * @return
     */
    public static DataSource get(String name, String key) {
        //参数校验
        Objects.requireNonNull(name);
        Objects.requireNonNull(key);
        return null;
    }
}
