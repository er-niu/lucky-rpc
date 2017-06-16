# 日志说明
    slfj、log4j、logback
    slfj：定义了一些接口
    log4j：是slfj的一种实现
    logback：也是slfj的一种实现
---
# 注意
    需要注意log4j中的FCQN参数
    FQCN，这里可以定义为MyLoggerUtils.class.getName()。这样在处理的时候会跳过MyLoggerUtils.class，会继续找上一层，如果你有多层(打印日志在父类中)，那么FQCN应该设置为实际类的最接近的一层。
    如果封装logutil的话，需要特别注意改参数，否则日志打印的位置不对
---
# jar冲突
    由于slf4j是标准，后面的log4j和logback都是实现在这套的标准，所以再封装日志的时候要注意
    log4j的jar包只引入log4j即可
    logback也指引入logback-classsic即可，logback会自动把slfj的标准给引入，防止冲突
    slf4j-api.jar里的org.slf4j.LoggerFactory会调org.slf4j.impl.StaticLoggerBinder，这个StaticLoggerBinder在logback-classic.jar里实现，这样就把slf4j的日志绑定到logback了。这时，如果classpath里同时还有slf4j-log4j12.jar那么会报multiple SLF4j bindings错误，
    因为slf4j-log4j12.jar里也有StaticLoggerBinder实现：
# 参考链接
   * [log4j.xml中的几个使用心得和说明](http://blog.sina.com.cn/s/blog_8f99a1640102v3eo.html)
   * [log4j 的实现原理](http://blog.sina.com.cn/s/blog_6cb15f8f0100ud0n.html)
   * [log4j日志封装说明—slf4j对于log4j的日志封装-正确获取调用堆栈](http://www.coderli.com/log4j-slf4j-logger-linenumber/)
   * [Log4j的基本配置和个人理解](blog.csdn.net/beyond667/article/details/8847318)
   * [slf4j log4j logback关系详解和相关用法](http://www.cnblogs.com/Sinte-Beuve/p/5758971.html)

---