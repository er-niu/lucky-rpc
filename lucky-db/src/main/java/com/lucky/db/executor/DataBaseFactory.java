package com.lucky.db.executor;

import com.lucky.db.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:chaoqiang.zhou
 * @Description:Executor的工厂类
 * @Date:Create in 9:51 2017/6/27
 */
public class DataBaseFactory {

    public static final Map<String, Executor> executorCache = new ConcurrentHashMap<>();

    public Executor open(String dbName) {
        Executor executor = executorCache.get(dbName);
        if (executor == null) {
            DataSource dataSource = DataSourceFactory.get(dbName);
            executor = new DataBase(dataSource);
            executorCache.putIfAbsent(dbName, executor);
        }
        return executor;
    }


    /**
     * @param dbName:dbname的信息
     * @param key
     * @return
     */
    public Executor openReadShard(String dbName, Object key) {

        return null;
    }


    public Executor openWriteShard(String dbName, String key) {

        return null;
    }
}
