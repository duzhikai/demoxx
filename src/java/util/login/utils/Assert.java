package util.login.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.util.exception.CheckException;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Assert {

    private Assert() {
    }

    /**
     * 是否为真，不为真时，抛出枚举类定义的异常信息
     *
     * @param expression    判断条件
     */
    public static void isTrue(boolean expression, int code, String msg) {
        if (!expression) {
            throw new CheckException(code, msg );
        }
    }

    /**
     * 是否为真，为真时，抛出枚举类定义的异常信息
     *
     * @param expression    判断条件
     */
    public static void notTrue(boolean expression, int code, String msg) {
        if (expression) {
            throw new CheckException(code, msg );
        }
    }

    /**
     * 是否为空，不为空时，抛出枚举类定义的异常信息
     *
     * @param object        判空对象
     */
    public static void isNull(Object object, int code, String msg) {
        if (object != null) {
            throw new CheckException(code, msg );
        }
    }

    /**
     * 是否为空，不为空时，抛出枚举类定义的异常信息
     *
     * @param s             判空对象
     */
    public static void isNull(String s, int code, String msg) {
        if (!StringUtils.isEmpty(s)) {
            throw new CheckException(code, msg );
        }
    }

    /**
     * 是否为空，为空时，抛出枚举类定义的异常信息
     *
     * @param object        判空对象
     */
    public static void notNull(Object object, int code, String msg) {
        if (object == null) {
            throw new CheckException(code, msg );
        }
    }


    /**
     * 是否为空，为空时，抛出枚举类定义的异常信息
     *
     * @param str           字符串对象
     */
    public static void notNull(String str, int code, String msg) {
        if (StringUtils.isEmpty(str)) {
            throw new CheckException(code, msg );
        }
    }


    /**
     * 数组是否为空，为空时，抛出枚举类定义的异常信息
     *
     * @param array         判断数组对象
     */
    public static void notEmpty(Object[] array, int code, String msg) {
        if (ObjectUtils.isEmpty(array)) {
            throw new CheckException(code, msg );
        }
    }

    /**
     * 数组是否为空，不为空时，抛出枚举类定义的异常信息
     *
     * @param array         判断数组对象
     */
    public static void isEmpty(Object[] array, int code, String msg) {
        if (!ObjectUtils.isEmpty(array)) {
            throw new CheckException(code, msg );
        }
    }


    /**
     * 集合是否为空，为空时，抛出枚举类定义的异常信息
     *
     * @param collection    判断集合对象
     */
    public static void notEmpty(Collection<?> collection, int code, String msg) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new CheckException(code, msg );
        }
    }

    /**
     * 集合是否为空，不为空时，抛出枚举类定义的异常信息
     *
     * @param collection    判断集合对象
     */
    public static void isEmpty(Collection<?> collection, int code, String msg) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new CheckException(code, msg );
        }
    }

    /**
     * 集合是否为空，为空时，抛出枚举类定义的异常信息
     *
     * @param map           判断Map对象
     */
    public static void notEmpty(Map<?, ?> map, int code, String msg) {
        if (ObjectUtils.isEmpty(map)) {
            throw new CheckException(code, msg );
        }
    }

    /**
     * 集合是否为空，不为空时，抛出枚举类定义的异常信息
     *
     * @param map           判断Map对象
     */
    public static void isEmpty(Map<?, ?> map, int code, String msg) {
        if (!ObjectUtils.isEmpty(map)) {
            throw new CheckException(code, msg );
        }
    }

    /**
     * 集合中元素数量校验，大于size则抛出异常，抛出枚举类定义的异常信息
     *
     * @param collection    判断集合对象
     */
    public static void isSizeLimit(Collection<?> collection, int size, int code, String msg) {
        if (collection.size() > size) {
            throw new CheckException(code, msg );
        }
    }

    /**
     * 是否为0，为0抛出异常
     *
     * @param integer    数值
     */
    public static void isNotZero(Integer integer, int code, String msg) {
        if (integer == 0) {
            throw new CheckException(code, msg );
        }
    }

    /**
     * 是否为空，为空时，抛出枚举类定义的异常信息
     *
     * @param str           字符串对象
     */
    public static void numStr(String str, Pattern pattern, int code, String msg) {
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            throw new CheckException(code, msg );
        }

    }

    /**
     * 长度校验
     * @param str rucan
     * @param size daxiao
     * @param code code
     * @param msg msg
     */
    public static void isLimit(String str, int size, int code, String msg) {
        if (str.length() > size) {
            throw new CheckException(code, msg );
        }
    }


}
