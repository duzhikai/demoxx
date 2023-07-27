package util.login.common.http;

/**
 * @author xuliangliang.1995
 */
public class InvalidUrlException extends HttpException {

    public InvalidUrlException(String url) {
        super("Invalid url : " + url);
    }
}
