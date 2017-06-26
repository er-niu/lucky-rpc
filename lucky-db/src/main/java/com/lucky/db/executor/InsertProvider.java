package com.lucky.db.executor;

import com.lucky.db.executor.context.InsertContext;
import com.lucky.db.executor.result.BuildResult;
import com.lucky.db.executor.result.InsertResult;
import com.lucky.db.sqlbuilder.SQL;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:仿照建造者模式，插入的执行器操作
 * @Date:Create in 13:07 2017/6/26
 */
public class InsertProvider implements InsertContext {


    //table的名称操作
    @Setter
    private String tableName;
    private final SQL sqlBuilder = new SQL();
    private List<Object> args = new ArrayList<>();


    @Override
    public InsertContext columns(String... columns) {
        for (String column : columns) {
            //参数化配置，prepareStatement最后进行注入操作
            this.sqlBuilder.VALUES(column, "?");
        }
        return this;
    }

    @Override
    public InsertContext values(Object... values) {
        args.addAll(Arrays.asList(values));
        return this;
    }


    //增加一个入口，可以获取到执行的sql信息
    @Override
    public BuildResult print() {
        //add table name
        this.sqlBuilder.INSERT_INTO(tableName);
        return new BuildResult(this.args, sqlBuilder.toString());
    }

    //具体插入的逻辑操作
    @Override
    public InsertResult result() {
        return null;
    }
}
