package util.login.utils;

import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.example.util.constant.StatusCode;
import org.example.util.exception.CheckException;

import java.util.HashMap;

@Slf4j
public class ReqParamUtil {

    public static <T> T getParam(HashMap<String, Object> param, String name, T defaultValue, Class<T> clazz) {
        Object obj = param.get(name);
        if (ObjectUtils.isNotEmpty(obj)) {
            try {
                return MapUtil.get(param, name, clazz);
            } catch (Exception e) {
                log.error("error", e);
                throw new CheckException(StatusCode.EC_PARAM_INVALID.getCode(), "参数类型转换异常");
            }
        }
        return defaultValue;
    }

    public static <T> T getParam(HashMap<String, Object> param, String name,  Class<T> clazz) {
        return getParam(param, name, null, clazz);
    }

    /**
     * 获取必传，如果为空抛出异常
     * @param param
     * @param name 变量名称
     * @param clazz 变量类型
     * @param <T>
     * @return
     */
    public static <T> T getParamOrFail(HashMap<String, Object> param, String name, Class<T> clazz) {
        T value = getParam(param, name, null, clazz);
        Assert.notNull(value, StatusCode.EC_PARAM_INVALID.getCode(), String.format(StatusCode.EC_PARAM_INVALID.getErrMsg(), name));
        return value;
    }
}
