package util.login.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.util.constant.RedisKeyConstant;
import org.example.util.redis.JedisPoolUtils;
import redis.clients.jedis.Jedis;

import java.util.Objects;

@Slf4j
public class RedisLockUtil {

    // 5分钟
    public static Integer expireTime = 300000;

    public static boolean getLock(String key) {
        return getLock(key, expireTime);
    }

    public static boolean getLock(String key, Integer expireTime) {
        expireTime = Objects.isNull(expireTime) ? RedisKeyConstant.LOCK_EX : expireTime;
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Long num = jedis.incrBy(key, 1L);
            jedis.expire(key, expireTime);
            if (num > 1) {
                log.error("获取锁失败 key : " + key + ", num : " + num + ", time :" + DateUtil.current());
                return false;
            }
            log.info("获取锁成功 key : " + key + ", num : " + num + ", time :" + DateUtil.current());
            return true;
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    public static boolean releaseLock(String key) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Long del = jedis.del(key);
            log.info("释放锁成功 key : " + key + ", del : " + del + ", time :" + DateUtil.current());
            return true;
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }
}
