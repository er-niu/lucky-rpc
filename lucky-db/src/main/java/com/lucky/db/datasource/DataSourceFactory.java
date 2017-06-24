package com.lucky.db.datasource;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @Author:chaoqiang.zhou
 * @Description:数据源的工厂
 * @Date:Create in 17:34 2017/6/24
 */
public class DataSourceFactory {

    //缓存需要的数据源信息，懒加载模式
    private HashMap<String, DataSource> dataSourceContainer = new HashMap<>();


    public static DataSource get(String name) {

        return null;
    }
}
