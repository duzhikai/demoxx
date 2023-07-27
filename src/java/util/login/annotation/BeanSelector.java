package util.login.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface BeanSelector {
    /**
     * 子类标识
     * @return
     */
    String[] value();
}
