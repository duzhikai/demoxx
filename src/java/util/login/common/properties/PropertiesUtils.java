package util.login.common.properties;

import java.util.Properties;
import java.util.Set;

/**
 * @author xuliangliang.1995
 */
public class PropertiesUtils {

    /**
     * copy properties
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Properties source, Properties target) {
        if (source != null) {
            Set<String> keys = source.stringPropertyNames();
            for (String key : keys) {
                target.setProperty(key, source.getProperty(key));
            }
        }
    }
}
