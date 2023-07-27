package util.login.common.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * @author xuliangliang.1995
 */
public class PercentageUtils {

    /**
     * 获取百分比
     * @param numerator 分子
     * @param denominator 分母
     * @return
     */
    public static Optional<String> toPercentage(BigDecimal numerator, BigDecimal denominator) {
        if (denominator != null && denominator.intValue() > 0) {
            return Optional.of(Optional.ofNullable(numerator).orElse(BigDecimal.ZERO)
                    .multiply(new BigDecimal(100))
                    .divide(denominator, 2, RoundingMode.HALF_UP)
                    .stripTrailingZeros()
                    .toPlainString().concat("%"));
        }
        return Optional.empty();
    }

    /**
     * 获取增长百分比
     * @param value1 新值（分子）
     * @param value2 旧值（分母）
     * @return
     */
    public static Optional<String> toIncreasedPercentage(BigDecimal value1, BigDecimal value2) {
        if (value2 != null && value2.intValue() > 0) {
            return Optional.of(Optional.ofNullable(value1).orElse(BigDecimal.ZERO)
                    .subtract(value2)
                    .multiply(new BigDecimal(100))
                    .divide(value2, 2, RoundingMode.HALF_UP)
                    .stripTrailingZeros()
                    .toPlainString().concat("%"));
        }
        return Optional.empty();
    }
}
