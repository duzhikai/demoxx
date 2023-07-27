//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package util.login;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

public class ObjectUtils {
    public ObjectUtils() {
    }

    public static void notNull(@Nullable Object object, RespCode respCode) {
        if (Objects.isNull(object)) {
            throw new CheckException(respCode.getCode(), respCode.getMsg());
        }
    }

    public static void notEmpty4Collection(@Nullable Collection<?> collection, RespCode respCode) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new CheckException(respCode.getCode(), respCode.getMsg());
        }
    }

    public static void notBlank(@Nullable String target, RespCode respCode) {
        if (StringUtils.isBlank(target)) {
            throw new CheckException(respCode.getCode(), respCode.getMsg());
        }
    }

    public static void validate(Supplier<Boolean> supplier, RespCode respCode) {
        if (!(Boolean)supplier.get()) {
            throw new CheckException(respCode.getCode(), respCode.getMsg());
        }
    }

    public static void validate(Supplier<RespCode> supplier) {
        if (!Constants.SUCCESS_CODE.equals(((RespCode)supplier.get()).getCode())) {
            throw new CheckException(((RespCode)supplier.get()).getCode(), ((RespCode)supplier.get()).getMsg());
        }
    }
}
