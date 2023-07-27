package util.login.annotation;

import java.lang.annotation.*;

/**
 * @className: RedisKey
 * @description: TODO 类描述
 * @author: maozhanlei
 * @date: 2022/5/17
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisKey {

    RedisStructure type();

    String des() default "";

    Class referEntity() default Object.class;

}
