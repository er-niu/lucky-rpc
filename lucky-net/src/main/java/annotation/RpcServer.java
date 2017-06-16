package annotation;

import remoting.server.FailMode;

import java.lang.annotation.*;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:42 2017/6/14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface RpcServer {
    /**
     * 服务名称
     * @return
     */
    String name() default "";

    /**
     * 服务描述
     * @return
     */
    String description() default "";


    /**
     * 失败处理模式
     * @return
     */
    FailMode fail() default FailMode.FailOver;
}
