package util.login.redis;

import org.example.util.beanSelector.BeanContent;
import org.example.util.config.EnvConfig;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;


/**
 * @author bytedance
 */
@Component
public class JedisPoolUtils {

    public static Jedis getJedis() {
        RedisSelector redisSelector = BeanContent.getBean(EnvConfig.environment + "_redis");
        return redisSelector.getRedis();
    }

    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
