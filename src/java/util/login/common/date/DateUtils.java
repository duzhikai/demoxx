package util.login.common.date;


import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author bytedance
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss:SSS";

    public static final String RFC3339 = "yyyy-MM-dd'T'HH:mm:ssXXX";

    /**
     * @return current time
     */
    public static Date now() {
        return new Date();
    }

    /**
     * @return formatted string by pattern
     */
    public static String format(Date date, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(date.toInstant().atZone(ZoneId.systemDefault()));
    }

    /**
     * convert {@link Date} to int val like 20220101
     * @return 20220101
     */
    public static int intDateNo(Date date) {
        String dateNoStr = format(date, "yyyyMMdd");
        return Integer.parseInt(dateNoStr);
    }
}
