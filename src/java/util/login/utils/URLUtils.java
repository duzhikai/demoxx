package util.login.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

@Slf4j
public class URLUtils {

    public static String urlencode(String str){
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("URLEncoder.encode exception :: ", e);
        }

        return null;
    }

    public static String urldecode(String str){
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("URLDecoder.decode exception :: ", e);
        }

        return null;
    }

    /**
     * 拼接为url
     * @param baseUrl
     * @param param
     * @return
     */
    public static String appendUrl(String baseUrl, HashMap<String, Object> param) {

        StringBuilder sb = new StringBuilder(baseUrl);
        param.forEach((key, value)-> sb.append("&" + key + "=" + value));
        return sb.toString().replaceFirst("&", "?");
    }
}
