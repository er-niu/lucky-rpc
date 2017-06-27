package com.lucky.db.executor;

import com.lucky.db.executor.context.DeleteContext;
import com.lucky.db.executor.result.BasicResult;
import com.lucky.db.executor.result.BuildResult;
import com.lucky.db.sqlbuilder.SQL;
import lombok.Setter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:删除语句的上下文得执行器操作
 * @Date:Create in 13:50 2017/6/26
 */
public class DeleteProvider implements DeleteContext {

    @Setter
    private String tableName;
    private final SQL sqlBuilder = new SQL();
    private List<Object> args = new ArrayList<>();
    private DataSource dataSource;

    public DeleteProvider(String tableName, DataSource dataSource) {
        this.tableName = tableName;
        this.dataSource = dataSource;
    }

    @Override
    public DeleteContext where(String field, ConditionType type, Object value) {
        //a.id+""+"<="+"?"
        //最后进行参数化操作
        this.sqlBuilder.WHERE(field + "" + type.name() + " ?");
        args.add(value);
        return this;
    }

    @Override
    public DeleteContext or() {
        this.sqlBuilder.OR();
        return this;
    }

    @Override
    public DeleteContext and() {
        this.sqlBuilder.AND();
        return this;
    }

    @Override
    public BuildResult print() {
        this.sqlBuilder.DELETE_FROM(this.tableName);
        return new BuildResult(args, this.sqlBuilder.toString());
    }



    //具体得实现操作，后期在做
    @Override
    public BasicResult result() {
        return null;
    }
}
