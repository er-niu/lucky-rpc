package com.lucky.db.executor.context;

import com.lucky.db.executor.ConditionType;
import com.lucky.db.executor.Sorter;

/**
 * @Author:chaoqiang.zhou
 * @Description:需要继承操作信息
 * @Date:Create in 17:53 2017/6/26
 */
public interface SelectContext extends SQLContext {


    public SelectContext SELECT(String columns);

    public SelectContext SELECT_DISTINCT(String columns);

    public SelectContext FROM(String table);

    public SelectContext JOIN(String join);

    public SelectContext INNER_JOIN(String join);

    public SelectContext LEFT_OUTER_JOIN(String join);

    public SelectContext RIGHT_OUTER_JOIN(String join);

    public SelectContext OUTER_JOIN(String join);

    public SelectContext WHERE(String conditions, ConditionType type,Object value);

    public SelectContext OR();

    public SelectContext AND();

    public SelectContext GROUP_BY(String... columns);

    public SelectContext HAVING(String conditions);
    public SelectContext ORDER_BY(String columns);

    public SelectContext ORDER_BY(String columns, Sorter sorter);

    //todo:扩展分页的查询语句操作
    public SelectContext LIMIT(String... columns);
}
