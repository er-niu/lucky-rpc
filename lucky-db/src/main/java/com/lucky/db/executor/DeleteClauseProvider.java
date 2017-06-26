package com.lucky.db.executor;

import com.lucky.db.executor.context.DeleteClause;
import com.lucky.db.executor.result.BuildResult;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:15 2017/6/26
 */
public class DeleteClauseProvider implements DeleteClause {
    @Override
    public BuildResult print() {
        return null;
    }

    @Override
    public <T> T result() {
        return null;
    }


}
