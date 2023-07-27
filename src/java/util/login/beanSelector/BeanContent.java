package util.login.beanSelector;


import org.example.util.annotation.BeanSelector;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class BeanContent implements ApplicationListener<ApplicationReadyEvent> {

    public static Map<String, Object> beanMap = new HashMap<>();

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Map<String, Object> beansWithAnnotation = event.getApplicationContext().getBeansWithAnnotation(BeanSelector.class);
        beansWithAnnotation.forEach((k, v) -> {
            Class<?> clazz = v.getClass();
            String[] value = clazz.getAnnotation(BeanSelector.class).value();
            for (String name : value) {
                beanMap.put(name, v);
            }
        });
    }


    public static <T> T getBean(String beanSelector) {

        return (T) beanMap.get(beanSelector);
    }
}
