//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package util.login;

import java.util.Optional;

public interface Convert {
    Integer getCode();

    String getDesc();

    static String getDescByCode(Integer code, Class<? extends Convert> type) {
        if (code == null) {
            return null;
        } else {
            if (type.isEnum()) {
                Convert[] var2 = (Convert[])type.getEnumConstants();
                int var3 = var2.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    Convert st = var2[var4];
                    if (st.getCode().equals(code)) {
                        return st.getDesc();
                    }
                }
            }

            return null;
        }
    }

    static <T extends Convert> Optional<T> getByCode(Integer code, Class<T> type) {
        if (code == null) {
            return Optional.empty();
        } else {
            if (type.isEnum()) {
                Convert[] var2 = (Convert[])type.getEnumConstants();
                int var3 = var2.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    Convert st = var2[var4];
                    if (st.getCode().equals(code)) {
                        return Optional.of(type.cast(st));
                    }
                }
            }

            return Optional.empty();
        }
    }
}
