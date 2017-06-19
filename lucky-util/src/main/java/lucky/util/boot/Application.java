package lucky.util.boot;

import lucky.util.ioc.ServiceLocatorAutoConfig;
import lucky.util.lang.StringConverter;
import lucky.util.lang.UncheckedException;
import lucky.util.log.Logger;
import lucky.util.log.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

/**
 * @Author:chaoqiang.zhou
 * @Description:应用启动类，在springboot上面包装了一层操作
 * @Date:Create in 19:00 2017/6/19
 */
public class Application {
    protected Class<?> bootClass;
    protected Logger logger;
    protected SpringApplication app;
    protected ApplicationContext ctx;
    //是否上报，该信息，后续进行加入
    protected boolean reportEnable = true;
    String[] args;
    LocalDateTime startTime;


    public Application(Class<?> bootClass, String[] args) {
        Objects.requireNonNull(bootClass, "bootclass can't be null");
        Objects.requireNonNull(args, "args can not b null");
        this.bootClass = bootClass;
        this.args = args;
        this.app = new SpringApplication(ServiceLocatorAutoConfig.class, bootClass);
        this.logger = LoggerFactory.getLogger(getClass());

    }


    /**
     * initialize settings, triggered before spring application run
     */
    protected void initialize() {
        // load default spring config
        // set default properties
        Map<String, Object> defaultProps = new HashMap<>();
        initDefaultProperties(defaultProps);
        if (defaultProps.size() > 0) {
            app.setDefaultProperties(defaultProps);
        }
    }

    protected void initDefaultProperties(Map<String, Object> props) {
        // hidden welcome banner
        props.put("spring.main.show-banner", false);
        // set config file name to app.properties name and its location
        // TODO classpath:/etc/spring.boot.properties:deprecated, retain for compatible, remove this version
        // TODO to extract to ConfigManager
        props.put("spring.config.name", "app");
        //获取根目录
        props.put("spring.config.location", MessageFormat.format("{0}/config/spring.boot.properties,{0}/config", this.getClass().getResource("/").getPath()));
        String[] excludes = new String[]{
                "org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration",
                "org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration",
                "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
                "org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration"
        };
        props.put("spring.autoconfigure.exclude", StringConverter.toString(",", excludes));
    }

    /**
     * load application components, triggered after spring application run
     */
    protected void load() {
        // leave it to child to inject
    }

    /**
     * 设置程序配置源
     *
     * @param sources
     */
    public void setSources(Object... sources) {
        if (sources == null) {
            throw new UncheckedException("sources can not be null");
        }

        Set<Object> set = new HashSet<>(sources.length);
        for (Object src : sources) {
            set.add(src);
        }
        app.setSources(set);
    }


    public <T> T getBean(Class<T> clazz) {
        return getBean(clazz, true);
    }

    public <T> T getBean(Class<T> clazz, boolean autowire) {
        if (ctx == null) {
            throw new UncheckedException("you must wait for application running");
        }

        T bean = ctx.getBean(clazz);
        if (bean == null && !clazz.isInterface()) {
            try {
                bean = clazz.newInstance();
                if (autowire) {
                    ctx.getAutowireCapableBeanFactory().autowireBean(bean);
                }
            } catch (Exception e) {
                String error = String.format("create bean instance of [%s] failed", clazz.getName());
                throw new UncheckedException(error, e);
            }
        }
        return bean;
    }


    public ApplicationContext run() {
        startTime = LocalDateTime.now();
        ctx = app.run(this.args);
        this.load();
        logger.info("Application started.");
        return ctx;
    }

    public void run(Consumer<ApplicationContext> action) {
        action.accept(run());
    }
}
