package com.lucky.db.executor;

import com.lucky.db.executor.context.DeleteClause;
import com.lucky.db.executor.result.BuildResult;

import javax.sql.DataSource;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:15 2017/6/26
 */
public class DeleteClauseProvider implements DeleteClause {


    private DataSource dataSource;

    private List<Object> objects;

    public DeleteClauseProvider(DataSource dataSource, List<Object> objects) {
        this.dataSource = dataSource;
        this.objects = objects;
    }

    public DeleteClauseProvider(DataSource dataSource, Object obj) {
        this.dataSource = dataSource;
        this.objects.add(obj);
    }

    @Override
    public BuildResult print() {

        return null;
    }

    @Override
    public <T> T result() {
        return null;
    }


}
