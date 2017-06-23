package lucky.util.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ServiceLocatorAutoConfig {
    @Autowired
    private ApplicationContext ctx;


    public ServiceLocatorAutoConfig() {
    }

    @ConditionalOnMissingBean
    public ServiceLocator serviceLocator() {
        SpringServiceLocator l = new SpringServiceLocator();
        l.setApplicationContext(ctx);
        return l;
    }
}
