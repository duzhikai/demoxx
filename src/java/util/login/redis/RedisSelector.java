package util.login.redis;

import redis.clients.jedis.Jedis;

public interface RedisSelector {

    Jedis getRedis();
}
