package com.lucky.db.executor;

import com.lucky.db.executor.context.DeleteContext;
import com.lucky.db.executor.context.InsertContext;
import com.lucky.db.executor.context.InsertClause;
import com.lucky.db.sqlbuilder.SQL;

import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:仿照mybatis，详情见mybatis中得executor核心执行器接口
 * @Date:Create in 10:46 2017/6/26
 */
public interface Executor {


    InsertClause insert(Object obj);

    //insert(table).columns().values().result()
    InsertContext insert(String table);

    <T> InsertClause insert(List<T> objects);


    //删除语句操作,delete table where......
    DeleteContext delete(String table);

    SQL delete(Object obj);




    SQL update(String table);

    SQL update(Object obj);

    SQL update(Object obj, String... columns);

    SQL select(String column);

    SQL select(String... columns);

    SQL select(Class<?> clazz);

    SQL select(Object obj);

    //原生sql查询操作
    SQL execute(String sql, Object... args);
}
