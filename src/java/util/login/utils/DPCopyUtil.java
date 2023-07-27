package util.login.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import org.example.util.constant.StatusCode;
import org.example.util.exception.CheckException;
import org.springframework.cglib.core.ReflectUtils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

public class DPCopyUtil {

    private static Map<Class, CopyOptions> cacheMap = new HashMap<>();

    public static <K, T> T copyAndParse(K source, Class<T> target){
        T res = null;
        try {
            res = target.newInstance();
            // 下划线转驼峰
            BeanUtil.copyProperties(source, res, getCopyOptions(source.getClass()));
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CheckException(StatusCode.EC_PARAM_INVALID.getCode(), "参数类型转换异常");
        }
        return res;
    }

    private static CopyOptions getCopyOptions(Class source) {
        CopyOptions options = cacheMap.get(source);
        if (options == null) {
            // 不加锁，我们认为重复执行不会比并发加锁带来的开销大
            options = CopyOptions.create().setFieldMapping(buildFieldMapper(source));
            cacheMap.put(source, options);
        }
        return options;
    }

    private static Map<String, String> buildFieldMapper(Class source) {
        PropertyDescriptor[] properties = ReflectUtils.getBeanProperties(source);
        Map<String, String> map = new HashMap<>();
        for (PropertyDescriptor target : properties) {
            String name = target.getName();
            String camel = StrUtil.toCamelCase(name);
            if (!name.equalsIgnoreCase(camel)) {
                map.put(name, camel);
            }
            String under = StrUtil.toUnderlineCase(name);
            if (!name.equalsIgnoreCase(under)) {
                map.put(name, under);
            }
        }
        return map;
    }

}
