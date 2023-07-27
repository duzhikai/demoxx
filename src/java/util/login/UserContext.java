//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package util.login;

import com.alibaba.ttl.TransmittableThreadLocal;

public class UserContext {
    private static TransmittableThreadLocal<CurrentUser> threadLocal = new TransmittableThreadLocal();

    public UserContext() {
    }

    public static CurrentUser getCurrentUser() {
        return (CurrentUser)threadLocal.get();
    }

    public static void putCurrentUser(CurrentUser currentUser) {
        threadLocal.set(currentUser);
    }

    public static void removeCurrentUser() {
        threadLocal.remove();
    }
}
