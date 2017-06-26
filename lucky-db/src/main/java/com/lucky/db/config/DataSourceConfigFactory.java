package com.lucky.db.config;

/**
 * @Author:chaoqiang.zhou
 * @Description:包含了所有得datasource连接的信息
 * @Date:Create in 17:43 2017/6/24
 */

import com.lucky.db.exception.ConfigException;

import java.util.HashMap;

/**
 * <databases>
 * <database name="WorkFlowStatsWriteData" provider="mysql" driver="" url="">
 * <setting name="url" value=""/>
 * <setting name="MaxIdleConns" value=""/>
 * <setting name="MaxOpenConns" value=""/>
 * <setting name="Username" value=""/>
 * <setting name="Password" value=""/>
 * </database>
 * <database name="WorkFlowStatsReadData" provider="mysql" driver="${cmc.mysql.dirver} url=">
 * </database>
 * </databases>
 */
public class DataSourceConfigFactory {
    //包含了所有的数据源的操作信息,初始化的时候进行加载配置信息，路径是/config/database.conf
    private static HashMap<String, DataSourceConfig> dataSourceConfigs = new HashMap<>();


    //项目启动的时候就加载
    static {
        //静态方法进行初始化操作
    }

    //通过名称来获取
    public static DataSourceConfig get(String name) {
        DataSourceConfig dataSourceConfig = dataSourceConfigs.get(name);
        if (dataSourceConfig == null) {
            //配置的异常类信息
            throw new ConfigException("can not find db,name={}" + name);
        }
        return dataSourceConfig;
    }
}
