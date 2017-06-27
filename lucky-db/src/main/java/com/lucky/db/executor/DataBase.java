package com.lucky.db.executor;

import com.lucky.db.executor.context.*;
import com.lucky.db.executor.result.SelectClauseProvider;

import javax.sql.DataSource;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 9:51 2017/6/27
 */
public class DataBase implements Executor {


    public DataSource dataSource;

    public DataBase(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //插入语句信息
    @Override
    public InsertClause insert(Object obj) {
        return new InsertClauseProvider(dataSource, obj);
    }

    @Override
    public InsertContext insert(String table) {
        return new InsertProvider(table, dataSource);
    }

    @Override
    public <T> InsertClause insert(List<T> objects) {
        return new InsertClauseProvider(dataSource, objects);
    }


    //删除语句信息
    @Override
    public DeleteContext delete(String table) {

        return new DeleteProvider(table, dataSource);
    }

    @Override
    public DeleteClauseProvider delete(Object obj) {
        return new DeleteClauseProvider(this.dataSource, obj);
    }

    @Override
    public DeleteClauseProvider delete(List<Object> objs) {

        return new DeleteClauseProvider(this.dataSource, objs);
    }


    //更新语句操作
    @Override
    public UpdateContext update(String table) {
        return new UpdateProvider(table, dataSource);
    }

    @Override
    public UpdateClause update(Object obj) {

        return new UpdateClauseProvider(dataSource, obj);
    }

    @Override
    public UpdateClause update(Object obj, String... columns) {

        return new UpdateClauseProvider(dataSource, obj, columns);
    }


    //查询语句操作
    @Override
    public SelectContext select(String column) {
        return new SelectProvider(column, this.dataSource);
    }

    @Override
    public SelectContext select(String... columns) {
        return new SelectProvider(this.dataSource, columns);
    }


    //这两个如何区别
    @Override
    public SelectClause select(Class<?> clazz) {
        return new SelectClauseProvider(clazz, dataSource);
    }


    @Override
    public ExecuteClause execute(String sql, Object... args) {
        return null;
    }
}
