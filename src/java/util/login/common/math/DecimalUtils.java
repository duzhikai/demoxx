package util.login.common.math;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;


/**
 * @author xuliangliang.1995
 */
public class DecimalUtils {

    public static BigDecimal to(String str) {
        return StringUtils.isNotEmpty(str) && NumberUtils.isParsable(str) ? new BigDecimal(str) : null;
    }

    public static String to(BigDecimal val) {
        return val != null ? val.stripTrailingZeros().toPlainString() : StringUtils.EMPTY;
    }
}
