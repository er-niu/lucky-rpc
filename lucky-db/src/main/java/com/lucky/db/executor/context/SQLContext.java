package com.lucky.db.executor.context;

import com.lucky.db.executor.LockMode;
import com.lucky.db.executor.result.BuildResult;

/**
 * @Author:chaoqiang.zhou
 * @Description:最抽象的接口信息
 * @Date:Create in 13:04 2017/6/26
 */
public interface SQLContext {
    BuildResult print();

    <T> T result();


}
