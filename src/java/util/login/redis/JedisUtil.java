package util.login.redis;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.exceptions.JedisDataException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Slf4j
public abstract class JedisUtil {
    private static final int DEFAULT_SETEX_TIMEOUT = 60 * 60;// setex的默认时间

    public static Boolean lock(String key, String value, Integer seconds) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            String result = jedis.set(key, value, "NX", "EX", Objects.isNull(seconds) ? DEFAULT_SETEX_TIMEOUT : seconds);
            return "OK".equalsIgnoreCase(result);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    public static JSONArray hallByIndex(String cacheKey, String fieldIndex, String valueIndex) {

        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Map<String, String> results = jedis.hgetAll(cacheKey);
            JSONArray resultAll = new JSONArray();
            int num = results.size() / 2;
            for (int i = 0; i < num; i++) {
                int key = i * 2;
                JSONArray content = new JSONArray(Lists.newArrayList(Collections.singletonMap(fieldIndex, results.get(key + "")), Collections.singletonMap(valueIndex, results.get(key + 1 + ""))));
                resultAll.add(content);
            }
            return resultAll;
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 获取key剩余时间
     *
     * @param key
     * @return
     */
    public static Long ttl(String key) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.ttl(key);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 获取hash表中对应key的值
     *
     * @param key 键
     * @return 值
     */
    public static <T> Map<String, T> hgetAll(String key, Class<T> clazz) {
        Jedis jedis = null;
        Map<String, T> result = Maps.newHashMap();
        try {
            jedis = JedisPoolUtils.getJedis();
            Map<String, String> stringStringMap = jedis.hgetAll(key);
            for (Map.Entry<String, String> entry : stringStringMap.entrySet()) {
                result.put(entry.getKey(), deSeri(entry.getValue().getBytes(StandardCharsets.UTF_8), clazz));
            }
            return result;
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    public static Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.hgetAll(key);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 设置hash表中对应key的值
     *
     * @param key 键
     * @return 值
     */
    public static <T> String hmset(String key, Map<String, T> fieldValue) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Map<String, String> keyvalue = Maps.newHashMap();
            for (Map.Entry<String, T> entry : fieldValue.entrySet()) {
                keyvalue.put(entry.getKey(), Arrays.toString(enSeri(entry.getValue())));
            }
            return jedis.hmset(key, keyvalue);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }


    /**
     * 获取hash表中对应key的值
     *
     * @param hashName hash名
     * @param key      键
     * @return 值
     */
    public static String getHashKV(String hashName, String key) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.hget(hashName, key);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 删除hash表中的键值对
     *
     * @param hashName hash名
     * @param key      键
     * @return 删除数量
     */
    public static Long delHashKV(String hashName, String key) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.hdel(hashName, key);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 存放hash键值对
     *
     * @param hashName hash名
     * @param key      键
     * @param value    值
     * @return 存放的数量
     */
    public static Long setHashKV(String hashName, String key, String value) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.hset(hashName, key, value);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * Redis hlen 命令用于返回列表的长度
     *
     * @param hashName hash名
     * @return 存放的数量
     */
    public static Long hlen(String hashName) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.hlen(hashName);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 获取所有元素
     *
     * @param hashName
     * @return
     */
    public static Set<String> hkeys(String hashName) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.hkeys(hashName);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 判断是否存在
     *
     * @return
     */
    public static Boolean hexists(String hashName, String key) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.hexists(hashName, key);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }


    public static void setExpire(String key, int timeout) {
        if (isValueNull(key, timeout)) {
            return;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            jedis.expire(key, timeout);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 添加一个字符串值,成功返回1,失败返回0
     *
     * @param key
     * @param value
     * @return
     */
    public static int set(String key, String value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            if ("ok".equalsIgnoreCase(jedis.set(key, value))) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }

    }

    /**
     * 缓存一个字符串值,成功返回1,失败返回0,默认缓存时间为1小时,以本类的常量DEFAULT_SETEX_TIMEOUT为准
     *
     * @param key
     * @param value
     * @return
     */
    public static int setEx(String key, String value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            if ("ok".equalsIgnoreCase(jedis.setex(key, DEFAULT_SETEX_TIMEOUT, value))) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 缓存一个字符串值,成功返回1,失败返回0,缓存时间以timeout为准,单位秒
     *
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public static int setEx(String key, String value, int timeout) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            if ("ok".equalsIgnoreCase(jedis.setex(key, timeout, value))) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 添加一个指定类型的对象,成功返回1,失败返回0
     *
     * @param key
     * @param value
     * @return
     */
    public static <T> int set(String key, T value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            byte[] data = enSeri(value);
            if ("ok".equalsIgnoreCase(jedis.set(key.getBytes(), data))) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 缓存一个指定类型的对象,成功返回1,失败返回0,默认缓存时间为1小时,以本类的常量DEFAULT_SETEX_TIMEOUT为准
     *
     * @param key
     * @param value
     * @return
     */
    public static <T> int setEx(String key, T value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            byte[] data = enSeri(value);
            if ("ok".equalsIgnoreCase(jedis.setex(key.getBytes(), DEFAULT_SETEX_TIMEOUT, data))) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 缓存一个指定类型的对象,成功返回1,失败返回0,缓存时间以timeout为准,单位秒
     *
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public static <T> int setEx(String key, T value, int timeout) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            byte[] data = enSeri(value);
            if ("ok".equalsIgnoreCase(jedis.setex(key.getBytes(), timeout, data))) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 将一个数值+1,成功返回+后的结果,失败返回null
     *
     * @param key
     * @return
     * @throws JedisDataException
     */
    public static Long incr(String key) throws JedisDataException {
        if (isValueNull(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.incr(key);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 将一个数值-1,成功返回-后的结果,失败返回null
     *
     * @param key
     * @return
     * @throws JedisDataException
     */
    public static Long decr(String key) throws JedisDataException {
        if (isValueNull(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.decr(key);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 先进先出队列
     *
     * @param key
     * @param value
     * @return
     */
    public static int pushQueue(String key, String... value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Long result = jedis.lpush(key, value);
            if (result != null && result != 0) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 先进先出队列
     *
     * @param key
     * @return
     */
    public static String popQueue(String key) {
        if (isValueNull(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            String result = jedis.lpop(key);
            if (result != null && result != "") {
                return result;
            } else {
                return "";
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 添加一个字符串值到list中,,成功返回1,失败返回0
     *
     * @param key
     * @param value
     * @return
     */
    public static int setList(String key, String... value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Long result = jedis.rpush(key, value);
            if (result != null && result != 0) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 缓存一个字符串值到list中,全部list的key默认缓存时间为1小时,成功返回1,失败返回0
     *
     * @param key
     * @param value
     * @return
     */
    public static int setExList(String key, String... value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Long result = jedis.rpush(key, value);
            jedis.expire(key, DEFAULT_SETEX_TIMEOUT);
            if (result != null && result != 0) {
                return 1;
            } else {
                return 0;
            }

        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 缓存一个字符串值到list中,全部list的key缓存时间为timeOut,单位为秒,成功返回1,失败返回0
     *
     * @param key
     * @param value
     * @return
     */
    public static int setExList(String key, int timeOut, String... value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Long result = jedis.rpush(key, value);
            jedis.expire(key, timeOut);
            if (result != null && result != 0) {
                return 1;
            } else {
                return 0;
            }

        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 添加一个<T>类型对象值到list中,成功返回1,失败返回0
     *
     * @param key
     * @param value
     * @return
     */
    @SafeVarargs
    public static <T> int setList(String key, T... value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            int res = 0;
            for (T t : value) {
                byte[] data = enSeri(t);
                Long result = jedis.rpush(key.getBytes(), data);
                if (result != null && result != 0) {
                    res++;
                }
            }
            if (res != 0) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 缓存一个<T>类型对象值到list中,全部list的key默认缓存时间为1小时,成功返回1,失败返回0
     *
     * @param key
     * @param value
     * @return
     */
    @SafeVarargs
    public static <T> int setExList(String key, T... value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            int res = 0;
            for (T t : value) {
                byte[] data = enSeri(t);
                Long result = jedis.rpush(key.getBytes(), data);
                if (result != null && result != 0) {
                    res++;
                }
            }
            jedis.expire(key, DEFAULT_SETEX_TIMEOUT);
            if (res != 0) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 缓存一个<T>类型对象值到list中,全部list的key缓存时间为timeOut,单位秒,成功返回1,失败返回0
     *
     * @param key
     * @param value
     * @return
     */
    @SafeVarargs
    public static <T> int setExList(String key, int timeOut, T... value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            int res = 0;
            for (T t : value) {
                byte[] data = enSeri(t);
                Long result = jedis.rpush(key.getBytes(), data);
                if (result != null && result != 0) {
                    res++;
                }
            }
            jedis.expire(key, timeOut);
            if (res != 0) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 添加一个List集合成功返回1,失败返回0
     *
     * @param key
     * @param value
     * @return
     * @throws IOException
     * @throws RuntimeException
     */
    public static <T> int setList(String key, List<T> value) throws RuntimeException, IOException {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            byte[] data = enSeriList(value);
            if ("ok".equalsIgnoreCase(jedis.set(key.getBytes(), data))) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 缓存一个List<T>集合,成功返回1,失败返回0,默认缓存时间为1小时,以本类的常量DEFAULT_SETEX_TIMEOUT为准
     *
     * @param key
     * @param value
     * @return
     * @throws IOException
     * @throws RuntimeException
     */

    public static <T> int setExList(String key, List<T> value) throws RuntimeException, IOException {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            byte[] data = enSeriList(value);
            if ("ok".equalsIgnoreCase(jedis.setex(key.getBytes(), DEFAULT_SETEX_TIMEOUT, data))) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 缓存一个List<T>集合,成功返回1,失败返回0,缓存时间以timeout为准,单位秒
     *
     * @param key
     * @param value
     * @param timeout
     * @return
     * @throws IOException
     * @throws RuntimeException
     */
    public static <T> int setExList(String key, List<T> value, int timeout) throws RuntimeException, IOException {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            byte[] data = enSeriList(value);
            if ("ok".equalsIgnoreCase(jedis.setex(key.getBytes(), timeout, data))) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 添加一个字符串到set,如果key存在就在就最追加,如果key不存在就创建,成功返回1,失败或者没有受影响返回0
     *
     * @param key
     * @param value
     * @return
     */
    public static int setSet(String key, String... value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Long result = jedis.sadd(key, value);
            if (result != null && result != 0) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 移除并返回集合中的一个随机元素
     *
     * @param key
     * @return
     */
    public static String popSet(String key) {
        if (isValueNull(key)) {
            return "";
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            String result = jedis.spop(key);
            if (result != null && result != "") {
                return result;
            } else {
                return "";
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 添加一个字符串set,如果key存在就在就最追加,整个set的key默认一小时后过期,如果key存在就在可以种继续添加,如果key不存在就创建,成功返回1,失败或者没有受影响返回0
     *
     * @param key
     * @param value
     * @return
     */
    public static int setExSet(String key, String... value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Long result = jedis.sadd(key, value);
            jedis.expire(key, DEFAULT_SETEX_TIMEOUT);
            if (result != null && result != 0) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 添加一个字符串set,如果key存在就在就最追加,整个set的key有效时间为timeOut时间,单位秒,如果key存在就在可以种继续添加,如果key不存在就创建,,成功返回1,失败或者没有受影响返回0
     *
     * @param key
     * @param value
     * @return
     */
    public static int setExSet(String key, int timeOut, String... value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Long result = jedis.sadd(key, value);
            jedis.expire(key, timeOut);
            if (result != null && result != 0) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 添加一个<T>类型到set集合,如果key存在就在就最追加,成功返回1,失败或者没有受影响返回0
     *
     * @param key
     * @param value
     * @return
     */
    @SafeVarargs
    public static <T> int setSet(String key, T... value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            int res = 0;
            for (T t : value) {
                byte[] data = enSeri(t);
                Long result = jedis.sadd(key.getBytes(), data);
                if (result != null && result != 0) {
                    res++;
                }
            }
            if (res != 0) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 缓存一个<T>类型到set集合,如果key存在就在就最追加,整个set的key默认有效时间为1小时,成功返回1,失败或者没有受影响返回0
     *
     * @param key
     * @param value
     * @return
     */
    @SafeVarargs
    public static <T> int setExSet(String key, T... value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            int res = 0;
            for (T t : value) {
                byte[] data = enSeri(t);
                Long result = jedis.sadd(key.getBytes(), data);
                if (result != null && result != 0) {
                    res++;
                }
            }
            jedis.expire(key, DEFAULT_SETEX_TIMEOUT);
            if (res != 0) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 缓存一个<T>类型到set集合,如果key存在就在就最追加,整个set的key有效时间为timeOut,单位秒,成功返回1,失败或者没有受影响返回0
     *
     * @param key
     * @param value
     * @return
     */
    @SafeVarargs
    public static <T> int setExSet(String key, int timeOut, T... value) {
        if (isValueNull(key, value)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            int res = 0;
            for (T t : value) {
                byte[] data = enSeri(t);
                Long result = jedis.sadd(key.getBytes(), data);
                if (result != null && result != 0) {
                    res++;
                }
            }
            jedis.expire(key, timeOut);
            if (res != 0) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 添加一个Map<K, V>集合,成功返回1,失败返回0
     *
     * @param key
     * @param value
     * @return
     */
    public static <K, V> int setMap(String key, Map<K, V> value) {
        if (value == null || key == null || "".equals(key)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            String data = JSONUtil.toJsonStr(value);
            if ("ok".equalsIgnoreCase(jedis.set(key, data))) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 缓存一个Map<K, V>集合,成功返回1,失败返回0,默认缓存时间为1小时,以本类的常量DEFAULT_SETEX_TIMEOUT为准
     *
     * @param key
     * @param value
     * @return
     */
    public static <K, V> int setExMap(String key, Map<K, V> value) {
        if (value == null || key == null || "".equals(key)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            String data = JSONUtil.toJsonStr(value);
            if ("ok".equalsIgnoreCase(jedis.setex(key, DEFAULT_SETEX_TIMEOUT, data))) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 缓存一个Map<K, V>集合,成功返回1,失败返回0,缓存时间以timeout为准,单位秒
     *
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public static <K, V> int setExMap(String key, Map<K, V> value, int timeout) {
        if (value == null || key == null || "".equals(key)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            String data = JSONUtil.toJsonStr(value);
            if ("ok".equalsIgnoreCase(jedis.setex(key, timeout, data))) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 获取一个字符串值
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        if (isValueNull(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.get(key);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 获得一个指定类型的对象
     *
     * @param key
     * @return
     */
    public static <T> T get(String key, Class<T> clazz) {
        if (isValueNull(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();

            byte[] data = jedis.get(key.getBytes());
            return deSeri(data, clazz);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 获得一个字符串集合,区间以偏移量 START 和 END 指定。 其中 0 表示列表的第一个元素， 1
     * 表示列表的第二个元素，以此类推。 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<String> getList(String key, long start, long end) {
        if (isValueNull(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.lrange(key, start, end);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 获得一个<T>类型的对象集合,区间以偏移量 START 和 END 指定。 其中 0 表示列表的第一个元素， 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static <T> List<T> getList(String key, long start, long end, Class<T> clazz) {
        if (isValueNull(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            List<byte[]> lrange = jedis.lrange(key.getBytes(), start, end);
            List<T> result = null;
            if (lrange != null) {
                for (byte[] data : lrange) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(deSeri(data, clazz));
                }
            }
            return result;
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 获得list中存了多少个值
     *
     * @return
     */
    public static long getListCount(String key) {
        if (isValueNull(key)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.llen(key);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 获得一个List<T>的集合,
     *
     * @param key   键
     * @param clazz 返回集合的类型
     * @return
     * @throws IOException
     */
    public static <T> List<T> getList(String key, Class<T> clazz) throws IOException {
        if (isValueNull(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            byte[] data = jedis.get(key.getBytes());
            return deSeriList(data, clazz);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 获得一个字符串set集合
     *
     * @param key
     * @return
     */
    public static Set<String> getSetCollection(String key) {
        if (isValueNull(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.smembers(key);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 获得一个字符串set集合
     *
     * @param key
     * @return
     */
    public static <T> Set<T> getSetCollection(String key, Class<T> clazz) {
        if (isValueNull(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Set<byte[]> smembers = jedis.smembers(key.getBytes());
            Set<T> result = null;
            if (smembers != null) {
                for (byte[] data : smembers) {
                    if (result == null) {
                        result = new HashSet<>();
                    }
                    result.add(deSeri(data, clazz));
                }
            }
            return result;
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 获得集合中存在多少个值
     *
     * @param key
     * @return
     */
    public static long getSetCount(String key) {
        if (isValueNull(key)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.scard(key);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 获得一个Map<v,k>的集合
     *
     * @param key
     * @param v
     * @param k
     * @return
     */
    public static <K, V> Map<K, V> getMap(String key, Class<K> k, Class<V> v) {
        if (key == null || "".equals(key)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            String data = jedis.get(key);
            @SuppressWarnings("unchecked")
            Map<K, V> result = (Map<K, V>) JSONUtil.parseObj(data);
            return result;
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 删除一个值
     *
     * @param key
     */
    public static void del(String... key) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            for (int i = 0; i < key.length; i++) {
                jedis.del(key);
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    public static boolean setNX(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            jedis.setnx(key, value);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
        return Boolean.TRUE;
    }

    public static String getSet(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.getSet(key, value);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    public static Long sAdd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.sadd(key, value);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    public static Long hSet(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            return jedis.hset(key, field, value);
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 加入元素
     *
     * @param key
     * @return
     */
    public static int zAdd(String key, double d, String value) {
        if (isValueNull(key, value, d)) {
            return 0;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Long result = jedis.zadd(key, d, value);
            if (result != null && result != 0) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 移除最小分值元素
     *
     * @param key
     * @return
     */
    public static String zPopMin(String key) {
        if (isValueNull(key)) {
            return "";
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Set<String> set = jedis.zrange(key, 0, 1);
            if (CollectionUtils.isNotEmpty(set)) {
                List<String> list = new ArrayList<>(set);
                Long result = jedis.zrem(key, list.get(0));
                if (result != null && result != 0) {
                    return list.get(0);
                } else {
                    return "";
                }
            }
            return "";
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    /**
     * 查询数量
     *
     * @param key
     * @return
     */
    public static long zCard(String key) {
        if (isValueNull(key)) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Long result = jedis.zcard(key);
            if (result != null && result != 0L) {
                return result;
            } else {
                return 0L;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    public static long zRem(String key, String taskId) {
        if (isValueNull(key, taskId)) {
            return 0L;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Long result = jedis.zrem(key, taskId);
            if (result != null && result != 0L) {
                return result;
            } else {
                return 0L;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    public static List<String> zRangeByScore(String key, double a, double b, int start, int count) {

        if (isValueNull(key, a, b, start, count)) {
            return null;
        }
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Set<String> strings = jedis.zrangeByScore(key, a, b, start, count);
            if (CollectionUtils.isNotEmpty(strings)) {
                return new ArrayList<>(strings);
            } else {
                return null;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }

    }

    /**
     * lua执行
     *
     * @param
     * @return
     */
    public static Object luaExecute(String script, List<String> keys, List<String> args) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();

            Object result = jedis.evalsha(jedis.scriptLoad(script), keys, args);
            if (result != null) {
                return result;
            } else {
                return 0L;
            }
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }


    // --------------------------公用方法区------------------------------------

    /**
     * 检查值是否为null,如果为null返回true,不为null返回false
     *
     * @param obj
     * @return
     */
    private static boolean isValueNull(Object... obj) {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] == null || "".equals(obj[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 序列化一个对象
     *
     * @param value
     * @return
     */
    private static <T> byte[] enSeri(T value) {
        @SuppressWarnings("unchecked")
        RuntimeSchema<T> schema = (RuntimeSchema<T>) RuntimeSchema.createFrom(value.getClass());
        return ProtostuffIOUtil.toByteArray(value, schema,
                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }

    /**
     * 反序列化一个对象
     *
     * @param
     * @return
     */
    protected static <T> T deSeri(byte[] data, Class<T> clazz) {
        if (data == null || data.length == 0) {
            return null;
        }
        RuntimeSchema<T> schema = RuntimeSchema.createFrom(clazz);
        T result = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, result, schema);
        return result;
    }

    /**
     * 序列化List集合
     *
     * @param list
     * @return
     * @throws IOException
     */
    private static <T> byte[] enSeriList(List<T> list) throws RuntimeException, IOException {
        if (list == null || list.size() == 0) {
            throw new RuntimeException("集合不能为空!");
        }
        @SuppressWarnings("unchecked")
        RuntimeSchema<T> schema = (RuntimeSchema<T>) RuntimeSchema.getSchema(list.get(0).getClass());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ProtostuffIOUtil.writeListTo(out, list, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        return out.toByteArray();
    }

    /**
     * 反序列化List集合
     *
     * @param data
     * @param clazz
     * @return
     * @throws IOException
     */
    private static <T> List<T> deSeriList(byte[] data, Class<T> clazz) throws IOException {
        if (data == null || data.length == 0) {
            return null;
        }
        RuntimeSchema<T> schema = RuntimeSchema.createFrom(clazz);
        List<T> result = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(data), schema);
        return result;
    }

    public static Set<String> scanKeys(String keyPattern) {
        Jedis jedis = null;
        try {
            jedis = JedisPoolUtils.getJedis();
            Set<String> keys = new HashSet<>();
            String cursor = ScanParams.SCAN_POINTER_START;
            ScanParams sp = new ScanParams();
            sp.match(keyPattern);
            sp.count(1000);

            do {
                ScanResult<String> ret = jedis.scan(cursor, sp);
                List<String> result = ret.getResult();
                if (result != null && result.size() > 0) {
                    keys.addAll(result);
                }
                //再处理cursor
                cursor = String.valueOf(ret.getCursor());
                // 迭代一直到cursor变为0为止
            } while (!cursor.equals(ScanParams.SCAN_POINTER_START));
            return keys;
        } finally {
            JedisPoolUtils.closeJedis(jedis);
        }
    }

    public abstract Set<String> iscanKeys(String keyPattern);
}
