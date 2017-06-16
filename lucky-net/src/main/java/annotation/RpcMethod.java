package annotation;

import remoting.server.FailMode;

import java.lang.annotation.*;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:52 2017/6/14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface RpcMethod {
    String name() default "";

    String description() default "";


    FailMode fail() default FailMode.FailOver;
}
