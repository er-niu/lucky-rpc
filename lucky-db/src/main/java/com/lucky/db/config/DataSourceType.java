package com.lucky.db.config;

import lucky.util.lang.EnumDisplayNameSupport;
import lucky.util.lang.EnumValueSupport;
import lucky.util.lang.Enums;

/**
 * @Author:chaoqiang.zhou
 * @Description:数据源连接池类型可以动态进行配置，默认是dbcp
 * @Date:Create in 18:09 2017/6/24
 */
//数据源的类型
public enum DataSourceType implements EnumValueSupport, EnumDisplayNameSupport {


    DBCP(1, "DBCP"),
    CSPO(2, "C3P0");
    private int value;
    private String displayName;

    DataSourceType(int value, String name) {
        this.value = value;
        this.displayName = name;
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public String displayName() {
        return this.displayName;
    }

    public static DataSourceType valueOf(int value) {
        return Enums.valueOf(DataSourceType.class, value);
    }

}
