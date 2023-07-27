package util.login.common.logic;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author xuliangliang.1995
 */
public class InvokeUtils {

    public static <T> void invokeIfNotNull(T t, Consumer<T> consumer) {
        if (t != null) {
            consumer.accept(t);
        }
    }

    public static <T, R> R invokeIfNotNull(T t, Function<T, R> function) {
        if (t != null) {
            return function.apply(t);
        }
        return null;
    }
}
