package com.lucky.db.sqlbuilder;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:11 2017/6/26
 */

/**
 * SQL,干货都在AbstractSQL里
 */
public class SQL extends AbstractSQL<SQL> {

    //fluent API
    @Override
    public SQL getSelf() {
        return this;
    }

}