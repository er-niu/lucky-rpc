package com.lucky.db.session;

/**
 * The primary Java interface for working with MyBatis.
 * Through this interface you can execute commands, get mappers and manage transactions
 */

import java.io.Closeable;

/**
 * 这是MyBatis主要的一个类，用来执行SQL，获取映射器，管理事务
 * <p>
 * 通常情况下，我们在应用程序中使用的Mybatis的API就是这个接口定义的方法。
 * 参照mybatis写的对外暴漏的执行器操作
 */
public interface SqlSession extends Closeable {

    <T> T selectOne(String statement);
}
