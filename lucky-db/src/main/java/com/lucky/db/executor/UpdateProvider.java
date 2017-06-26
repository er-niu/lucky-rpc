package com.lucky.db.executor;

import com.lucky.db.executor.context.UpdateContext;
import com.lucky.db.executor.result.BuildResult;
import com.lucky.db.executor.result.UpdateResult;
import com.lucky.db.sqlbuilder.SQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:27 2017/6/26
 */
public class UpdateProvider implements UpdateContext {

    private final String tableName;

    //后面考虑放置到threadlocal里面
    private SQL sqlBuilder = new SQL();
    //参数集合信息
    private List<Object> args = new ArrayList<>();

    public UpdateProvider(String tableName) {
        this.tableName = tableName;
    }


    @Override
    public UpdateContext columns(String... columns) {
        for (String column : columns) {
            //另类的拼接sql操作
            this.sqlBuilder.SET(column + " = ?");
        }
        return this;
    }

    @Override
    public UpdateContext values(Object... values) {
        this.args.addAll(Arrays.asList(values));
        return this;
    }

    @Override
    public UpdateContext where(String field, ConditionType type, Object value) {
        //拼接sql
        this.sqlBuilder.WHERE(field + "" + type.name() + " ?");
        //拼接参数
        this.args.add(value);
        return this;
    }

    @Override
    public UpdateContext or() {
        this.sqlBuilder.OR();
        return this;
    }

    @Override
    public UpdateContext and() {
        this.sqlBuilder.AND();
        return this;
    }

    @Override
    public BuildResult print() {
        return new BuildResult(args, this.sqlBuilder.toString());
    }

    @Override
    public UpdateResult result() {
        return null;
    }
}
