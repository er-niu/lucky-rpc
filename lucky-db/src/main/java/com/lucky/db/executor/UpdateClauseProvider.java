package com.lucky.db.executor;

import com.lucky.db.executor.context.UpdateClause;
import com.lucky.db.executor.result.BuildResult;
import com.mongodb.client.result.UpdateResult;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:51 2017/6/26
 */
public class UpdateClauseProvider implements UpdateClause {


    private DataSource dataSource;

    //代表需要更新的字段信息
    private List<String> updateColumns = new ArrayList<>();
    private Object object;

    public UpdateClauseProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public UpdateClauseProvider(DataSource dataSource, Object obj) {
        this.dataSource = dataSource;
        this.object = obj;
    }

    public UpdateClauseProvider(DataSource dataSource, Object obj, String... columns) {
        this.dataSource = dataSource;
        this.updateColumns = Arrays.asList(columns);
        this.object = obj;
    }

    @Override
    public BuildResult print() {
        return null;
    }

    @Override
    public UpdateResult result() {
        return null;
    }
}
