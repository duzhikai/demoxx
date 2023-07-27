package util.login.domain;

/**
 * Created by mao on 2022/6/12.
 */
public class LoginContext {

    private static final ThreadLocal<LoginInfo> LOGIN_USER_INFO_THREAD_LOCAL = new ThreadLocal<>();


    /**
     * 设置登录用户信息
     * @param loginUserInfo
     */
    public static void setLoginUser(LoginInfo loginUserInfo) {
        LOGIN_USER_INFO_THREAD_LOCAL.set(loginUserInfo);
    }

    /**
     * 获取用户登录信息
     * @return
     */
    public static LoginInfo getLoginInfo() {
        return LOGIN_USER_INFO_THREAD_LOCAL.get();
    }

    public static void removeLoginInfo() {
        LOGIN_USER_INFO_THREAD_LOCAL.remove();
    }
}
