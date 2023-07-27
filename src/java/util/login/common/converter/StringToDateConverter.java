package util.login.common.converter;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author xuliangliang.1995
 */
public class StringToDateConverter implements Converter<String, Date> {

    private static final Logger logger = LoggerFactory.getLogger(StringToDateConverter.class);

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Date convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        for (PatternEnum pattern : PatternEnum.values()) {
            boolean isMatched = pattern.compiledPattern.matcher(source).matches();
            if (isMatched) {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern.pattern);
                Date dateVal = null;
                try {
                    dateVal = sdf.parse(source);
                } catch (ParseException e) {
                    logger.error("Date format fail . value is {}", source);
                }
                return dateVal;
            }
        }
        return null;
    }

    /**
     * 支持转换的格式
     */
    private enum PatternEnum {
        YYYY_MM_DD_001("^\\d{4}/\\d{2}/\\d{2}$", "yyyy/MM/dd"),
        YYYY_MM_DD_002("^\\d{4}-\\d{2}-\\d{2}$", "yyyy-MM-dd"),
        YYYY_MM_DD_HH_MM_001("^\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm"),
        YYYY_MM_DD_HH_MM_002("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm"),
        YYYY_MM_DD_HH_MM_SS_001("^\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss"),
        YYYY_MM_DD_HH_MM_SS_002("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");

        PatternEnum(String regex, String pattern) {
            this.regex = regex;
            this.pattern = pattern;
            this.compiledPattern = Pattern.compile(regex);
        }

        private final String regex;
        private final String pattern;
        private final Pattern compiledPattern;

    }
}
