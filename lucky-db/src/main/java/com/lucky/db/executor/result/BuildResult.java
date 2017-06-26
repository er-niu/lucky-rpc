package com.lucky.db.executor.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:SQL的一个包装类操作
 * @Date:Create in 11:58 2017/6/26
 */
@Getter
@Setter
public class BuildResult {

    public List<Object> args;
    public String sql;


    public BuildResult() {
    }

    public BuildResult(List<Object> args, String sql) {
        this.args = args;
        this.sql = sql;
    }



}
