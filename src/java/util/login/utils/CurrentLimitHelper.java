package util.login.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.example.util.redis.JedisUtil;

import java.util.Arrays;
import java.util.List;


public class CurrentLimitHelper {

    public static final int MAX_POOL_SIZE = 2;

    //pdf算法解析进行中的数量
    public static final String PDF_RESOLVING_COUNT = "pdf_resolving_count";
    //pdf算法解析进行中参数详情
    public static final String PDF_RESOLVEING_DETAIL = "pdf_resolveing_detail_";

    //pdf算法解析中队列 (主要功能是将超过8小时的数据 如果没有回调则删除处理，队列中存放 task_id)
    public static final String PDF_RESOLVEING_QUEUE = "pdf_resolveing_queue";

    //pdf算法待解析队列 (队列中存放整个算法入参)
    public static final String PDF_WAIT_RESOLVE_QUEUE = "pdf_wait_resolve_queue";


    private static final Long EXPIRE = 28800000L;


    /**
     * 执行计数器自增
     *
     * @param key
     * @return
     */
    public static Boolean incResolving(String key, JSONObject param) {

        String lua = "local obj=KEYS[1]\n" +
                "        local limit=ARGV[1]\n" +
                "        local num=tonumber(redis.call('get',obj) or \"0\")\n" +
                "        if num+1>tonumber(limit) then\n" +
                "        return -1\n" +
                "        else\n" +
                "        redis.call(\"INCRBY\",obj,\"1\")\n" +
                "        return num+1\n" +
                "        end";

        Object o = JedisUtil.luaExecute(lua, Arrays.asList(key), Arrays.asList(String.valueOf(MAX_POOL_SIZE)));
        int result = Integer.parseInt(String.valueOf(o));
        if (result != -1) {
            String task_id = param.getStr("task_id");
            addQueue(CurrentLimitHelper.PDF_RESOLVEING_QUEUE, task_id);
            setDetail(PDF_RESOLVEING_DETAIL + task_id, JSONUtil.toJsonStr(param));
        }
        return result != -1;
    }

    /**
     * 执行计数器自减
     *
     * @param key
     * @return
     */
    public static Long decrResolving(String key, String taskId) {
        String s = JedisUtil.get(key);
        if (StringUtils.isEmpty(s)) {
            return 0L;
        }
        CurrentLimitHelper.deleteByKey(CurrentLimitHelper.PDF_RESOLVEING_QUEUE, taskId);
        CurrentLimitHelper.delDetail(PDF_RESOLVEING_DETAIL + taskId);
        return JedisUtil.decr(key);
    }

    /**
     * 拿到所有过期数据
     *
     * @param key
     * @return
     */
    public static List<String> getExpiredList(String key) {

        return JedisUtil.zRangeByScore(key, 0, DateUtil.current(), 0, -1);
    }

    /**
     * 放入待执行队列中
     *
     * @param key
     * @param jsonObject
     */
    public static void addQueue(String key, String jsonObject) {
        JedisUtil.zAdd(key, DateUtil.current() + EXPIRE, jsonObject);
    }

    public static void setDetail(String key, String jsonObject) {
        JedisUtil.set(key, jsonObject);
    }

    public static String getDetail(String key) {
        return JedisUtil.get(key);
    }

    public static void delDetail(String key) {
        JedisUtil.del(key);
    }


    /**
     * 取出待执行队列元素
     *
     * @param key
     * @return
     */
    public static JSONObject popQueue(String key) {
        String taskObject = JedisUtil.zPopMin(key);
        if (StringUtils.isNotEmpty(taskObject)) {
            return JSONUtil.parseObj(taskObject);
        }
        return null;
    }

    /**
     * 查询待执行的任务数
     */
    public static Long getWaitCount(String key) {
        return JedisUtil.zCard(key);
    }

    /**
     * 根据key删除队列元素
     */
    public static Long deleteByKey(String key, String taskId) {
        return JedisUtil.zRem(key, taskId);
    }
}
