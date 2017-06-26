package com.lucky.db.executor.context;

/**
 * @Author:chaoqiang.zhou
 * @Description:insert语句的上下文的操作
 * @Date:Create in 11:53 2017/6/26
 */
public interface InsertContext extends SQLContext {

    //buildresult对sql和arg进行了封装操作
    InsertContext columns(String... columns);

    InsertContext values(Object... values);


}
