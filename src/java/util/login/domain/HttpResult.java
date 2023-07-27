package util.login.domain;

import lombok.Data;

@Data
public class HttpResult<T> {

    private Integer code;

    private T data;

    private String msg;

    public HttpResult() {

    }

    public HttpResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public HttpResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public  static <T> HttpResult<T> success(T result) {
        return new HttpResult(0,"success", result);
    }

    public static HttpResult error(Integer code, String msg) {
        return new HttpResult(code, msg, null);
    }

}
