package com.lucky.db.executor;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:14 2017/6/26
 */
public enum Sorter {
    DESC("DESC"),
    ASC("ASC");
    String value;

    Sorter(String value) {
        this.value = value;
    }
}
