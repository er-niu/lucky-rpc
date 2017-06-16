//package lucky.test.log;
//
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Priority;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.impl.Log4jLoggerAdapter;
//
///**
// * @Author:chaoqiang.zhou
// * @Description:
// * @Date:Create in 15:34 2017/5/17
// */
//public class MyLog4j {
////    public static final Logger logger = LoggerFactory.getLogger("MyLog4j");
////    //    public final static String FQCN = Log4jLoggerAdapter.class.getName();
////    public final static String FQCN = MyLog4j.class.getName();
////
////    public final static org.apache.log4j.Logger log4j = LogManager.getLogger("test");
////
////
////    public static void info2(String msg) {
////        //todo:如果封装工具类的话，这样的信息，只会打印到该工具类中
////        log4j.info("测试类信息");
////        //FQCN，这里可以定义为MyLoggerUtils.class.getName()。这样在处理的时候会跳过MyLoggerUtils.class，会继续找上一层，如果你有多层(打印日志在父类中)，那么FQCN应该设置为实际类的最接近的一层。
////        /**
////         * final static String FQCN = Log4jLoggerAdapter.class .getName();
////         public void info(String msg) {
////         logger.log(FQCN, Level. INFO, msg, null );
////         }
////         */
////
////
////        log4j.log(FQCN, Priority.INFO, msg, null);
//////        logger.log(FQCN, Level. INFO, msg, null );
////    }
////
////    public static void info(String msg) {
////        Throwable throwable = new Throwable();
////        StackTraceElement[] ste = throwable.getStackTrace();
////        for (StackTraceElement stackTraceElement : ste) {
////            System.out
////                    .println("ClassName: " + stackTraceElement.getClassName());
////            System.out.println("Method Name: "
////                    + stackTraceElement.getMethodName());
////            System.out.println("Line number: "
////                    + stackTraceElement.getLineNumber());
////        }
////
////        System.out.println("------------------");
////
////        StackTraceElement[] ste2 = Thread.currentThread().getStackTrace();
////        for (StackTraceElement stackTraceElement : ste2) {
////            System.out
////                    .println("ClassName: " + stackTraceElement.getClassName());
////            System.out.println("Method Name: "
////                    + stackTraceElement.getMethodName());
////            System.out.println("Line number: "
////                    + stackTraceElement.getLineNumber());
////        }
////        logger.info(msg);
////    }
//}
