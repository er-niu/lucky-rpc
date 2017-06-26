package com.lucky.db.executor;

import com.lucky.db.executor.context.InsertClause;
import com.lucky.db.executor.result.BuildResult;
import com.lucky.db.executor.result.InsertResult;
import com.lucky.db.sqlbuilder.SQL;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 13:40 2017/6/26
 */
public class InsertClauseProvider implements InsertClause {

    private List<Object> objects = new ArrayList<>();
    private final SQL sqlBuilder = new SQL();

    @Override
    public BuildResult print() {
        return null;
    }



    @Override
    public InsertResult result() {
        return null;
    }
}
