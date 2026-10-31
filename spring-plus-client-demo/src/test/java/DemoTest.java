import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.thread.ThreadUtil;
import org.junit.jupiter.api.Test;
import org.spring.plus.caffeine.CaffeineSpringPlusCache;
import org.spring.plus.context.support.MessageSourceHolder;
import org.spring.plus.core.utils.CommonPathUtils;
import org.spring.plus.core.utils.MsgFormatUtils;
import org.spring.plus.serializer.JacksonService;
import org.spring.plus.serializer.JacksonUtil;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhanghaowei
 * @since 1.0
 */
public class DemoTest {

    @Test
    public void testI18n() {
        LocaleContextHolder.setLocale(Locale.CHINESE);
        Locale locale = LocaleContextHolder.getLocale();
        ReloadableResourceBundleMessageSource messageSource = MessageSourceHolder.getInstance();
        System.out.println(messageSource.getMessage("org.hibernate.validator.constraints.ISBN.message", null, locale));
    }


    @Test
    public void testMsgFormat() {
        List<Object> collect = Stream.of("可以", "2", 3, "4").collect(Collectors.toList());
        System.out.println(MsgFormatUtils.format("在 {0} 的充电订单已结束充电，请及时关注。订单尾号：{1}，充电时长：{2} 分钟，消费金额：{3} 元。", collect));
        String format = CharSequenceUtil.format("在 {} 的充电订单已结束充电，请及时关注。订单尾号：{}，充电时长：{} 分钟，消费金额：{} 元。", collect.toArray());
        System.out.println(format);
    }

    @Test
    public void webUtilsTest() {
        String suffix = CommonPathUtils.joinPaths("failed", "asdasd.xlsx");
        System.out.println(suffix);
    }

    @Test
    public void testsds() {
        CaffeineSpringPlusCache caffeineSpringPlusCache = new CaffeineSpringPlusCache(new JacksonService(JacksonUtil.createMapper()));
        caffeineSpringPlusCache.set("test", "123", 10L, TimeUnit.SECONDS);
        for (int i = 0; i < 10; i++) {
            String test = caffeineSpringPlusCache.get("test");
            System.out.println(test);
            ThreadUtil.safeSleep(5000);
        }
        ThreadUtil.safeSleep(50_000);

    }


    public static void main(String[] args) {

    }
}
