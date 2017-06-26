package com.lucky.db.executor.result;

/**
 * @Author:chaoqiang.zhou
 * @Description:抽象的结果集信息
 * @Date:Create in 13:32 2017/6/26
 */
public interface BasicResult {
    //获取结果
    Object result();

    //获取记录数
    int count();
}
