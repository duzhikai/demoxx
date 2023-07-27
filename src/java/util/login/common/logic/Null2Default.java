package util.login.common.logic;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @author xuliangliang.1995
 */
public class Null2Default {

    public static long of(Long val) {
        return of(val, 0L);
    }

    public static long of(Long val, long defaultVal) {
        return val != null ? val : defaultVal;
    }

    public static int of(Integer val) {
        return of(val, 0);
    }

    public static int of(Integer val, int defaultVal) {
        return val != null ? val : defaultVal;
    }

    public static short of(Short val) {
        return of(val, (short) 0);
    }

    public static short of(Short val, short defaultVal) {
        return val != null ? val : defaultVal;
    }

    public static String of(String val) {
        return of(val, StringUtils.EMPTY);
    }

    public static String of(String val, String defaultVal) {
        return val != null ? val : defaultVal;
    }

    public static BigDecimal of(BigDecimal val) {
        return of(val, BigDecimal.ZERO);
    }

    public static BigDecimal of(BigDecimal val, BigDecimal defaultVal) {
        return val != null ? val : defaultVal;
    }

}
