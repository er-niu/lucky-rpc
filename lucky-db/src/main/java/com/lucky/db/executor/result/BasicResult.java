package com.lucky.db.executor.result;

import lombok.Getter;

/**
 * @Author:chaoqiang.zhou
 * @Description:抽象的结果集信息,包含了最基本得返回结果的信息
 * @Date:Create in 13:32 2017/6/26
 */
public class BasicResult {
    //    //获取结果
//    Object result();
//
//    //获取记录数
//    int count();
    //更新、插入、删除需要返回受影响的行数
    @Getter
    private int affectedCount;


    public BasicResult() {

    }

    public BasicResult(int affectedCount) {
        this.affectedCount = affectedCount;
    }
}
