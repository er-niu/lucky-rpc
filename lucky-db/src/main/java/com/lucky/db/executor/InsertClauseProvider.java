package com.lucky.db.executor;

import com.lucky.db.executor.context.InsertClause;
import com.lucky.db.executor.result.BasicResult;
import com.lucky.db.executor.result.BuildResult;
import com.lucky.db.sqlbuilder.SQL;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:插入语句操作
 * @Date:Create in 13:40 2017/6/26
 */
public class InsertClauseProvider implements InsertClause {

    private List<Object> objects = new ArrayList<>();
    private SQL sqlBuilder = new SQL();

    public DataSource dataSource;

    public InsertClauseProvider(DataSource dataSource, List<Object> objs) {
        this.dataSource = dataSource;
        this.objects = objs;
    }

    public InsertClauseProvider(DataSource dataSource, Object obj) {
        this.dataSource = dataSource;
        this.objects.add(obj);
    }

    @Override
    public BuildResult print() {
        return null;
    }


    @Override
    public BasicResult result() {
        return null;
    }
}
