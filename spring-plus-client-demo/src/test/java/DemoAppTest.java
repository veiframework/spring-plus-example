import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.spring.plus.cache.SpringPlusCache;
import org.spring.plus.context.support.MessageSourceHolder;
import org.spring.plus.serializer.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.Formatter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.test.app.ClientDemoApp;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zhanghaowei
 * @since 1.0
 */
@Slf4j
@SpringBootTest(classes = {ClientDemoApp.class})
@AutoConfigureMockMvc
@MockBean(ServletServerContainerFactoryBean.class)
public class DemoAppTest {

    @Test
    public void testDemoAppI18n() {
        LocaleContextHolder.setLocale(Locale.CHINESE);
        Locale locale = LocaleContextHolder.getLocale();
        ReloadableResourceBundleMessageSource messageSource = MessageSourceHolder.getInstance();
        System.out.println(messageSource.getMessage("org.hibernate.validator.constraints.ISBN.message", null, locale));
    }

    @Autowired
    private List<Formatter<?>> formatters;

    @Test
    public void asdsad() {
        for (Formatter<?> formatter : formatters) {
            System.out.println(formatter.getClass().getName());
        }
    }


    @Autowired
    private SpringPlusCache springPlusCache;

    @Test
    public void testCache() {
        springPlusCache.addSet("zset", "zhangsan", 1);
        springPlusCache.addSet("zset", "lisi", 2);
        Set<String> zset = springPlusCache.keys("zset");
        System.out.println(JacksonUtil.instance().toJson(zset));
        Set<String> lessEqual = springPlusCache.getSet("zset", true, 1);
        System.out.println(JacksonUtil.instance().toJson(lessEqual));
    }

    @Test
    public void testCache2() {
        springPlusCache.set("test", "123", 10L, TimeUnit.SECONDS);
        for (int i = 0; i < 10; i++) {
            String test = springPlusCache.get("test");
            System.out.println(test);
            ThreadUtil.safeSleep(5000);
        }
        ThreadUtil.safeSleep(50_000);
    }

    @Autowired
    @Qualifier("caffeineSpringPlusCache")
    private SpringPlusCache caffeineSpringPlusCache;

    @Test
    public void testCache3() {
        caffeineSpringPlusCache.set("test", "123", 10L, TimeUnit.SECONDS);
        for (int i = 0; i < 10; i++) {
            String test = caffeineSpringPlusCache.get("test");
            System.out.println(test);
            ThreadUtil.safeSleep(5000);
        }
        ThreadUtil.safeSleep(50_000);
    }

}
