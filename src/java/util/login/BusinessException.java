//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package util.login;

public class BusinessException extends RuntimeException {
    private Integer code;
    private String msg;

    public BusinessException() {
    }

    public BusinessException(Integer code, String msg) {
        super(String.format("code=%d,msg=%s", code, msg));
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
