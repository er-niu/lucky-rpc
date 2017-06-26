package com.lucky.db.executor;

/**
 * @Author:chaoqiang.zhou
 * @Description:条件类型枚举类
 * @Date:Create in 13:53 2017/6/26
 */
public enum ConditionType {
    EQ("="), NE("<>"), LT("<"), GT(">"), LTE("<="), GTE(">="), IN("IN"), NIN("NOT IN"), LK("LIKE");

    String value;

    ConditionType(String value) {
        this.value = value;
    }
}
