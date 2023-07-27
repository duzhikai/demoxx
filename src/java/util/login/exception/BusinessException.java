package util.login.exception;


import util.login.constant.StatusCode;

/**
 * @author maozhanlei
 * @version 1.0
 * @date 2020/9/17 16:50
 **/
public class BusinessException extends RuntimeException {
    private Integer code;

    private String msg;

    public BusinessException() {
    }

    public BusinessException(Integer code, String msg) {
        super("code：" + code +  ", msg:" + msg );
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(StatusCode statusCode) {
        super("code：" + statusCode.getCode() +  ", msg:" + statusCode.getErrMsg() );
        this.code = statusCode.getCode();
        this.msg = statusCode.getErrMsg();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
