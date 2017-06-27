package com.lucky.db.executor;

import com.lucky.db.executor.context.ExecuteClause;
import com.lucky.db.executor.result.BuildResult;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:06 2017/6/27
 */
public class ExecutorProvider implements ExecuteClause {
    @Override
    public BuildResult print() {
        return null;
    }

    @Override
    public <T> T result() {
        return null;
    }
}
