package com.lucky.db.executor.context;

import com.lucky.db.executor.ConditionType;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:25 2017/6/26
 */
public interface UpdateContext extends SQLContext{
    //buildresult对sql和arg进行了封装操作
    UpdateContext columns(String... columns);

    UpdateContext values(Object... values);

    UpdateContext where(String field, ConditionType type, Object value);

    UpdateContext or();

    UpdateContext and();

}
