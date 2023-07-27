package util.login.common.http;

import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author xuliangliang.1995
 */
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static final String HTTP_JSON = "application/json; charset=utf-8";
    private static final String HTTP_FORM = "application/x-www-form-urlencoded; charset=utf-8";

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build();

    /**
     * get
     * @param url
     * @return
     */
    public static String httpGet(String url) {
        return httpGet(url, null);
    }

    /**
     * get
     * @param url
     * @param headers
     * @return
     */
    public static String httpGet(String url, Map<String, String> headers) {
        checkUrl(url);

        if (headers == null) {
            headers = new HashMap<>(0);
        }

        Request.Builder builder = new Request.Builder();
        headers.forEach(builder::header);
        Request request = builder.get().url(url).build();

        return executeRequest(request);
    }


    /**
     * post with json
     * @param url
     * @param json
     * @return
     */
    public static String httpPostWithJson(String url, String json) {
        return httpPostWithJson(url, null, json);
    }

    /**
     * post with json
     * @param url
     * @param headers
     * @param json
     * @return
     */
    public static String httpPostWithJson(String url, Map<String, String> headers, String json) {
        checkUrl(url);

        if (headers == null) {
            headers = new HashMap<>(0);
        }

        RequestBody body = RequestBody.create(MediaType.parse(HTTP_JSON), json);
        Request.Builder builder = new Request.Builder().url(url);
        headers.forEach(builder::addHeader);
        Request request = builder.post(body).build();

        return executeRequest(request);
    }


    /**
     * post with form
     * @param url
     * @param content
     * @param headers
     * @return
     */
    public static String httpPostWithForm(String url, String content, Map<String, String> headers) {
        checkUrl(url);

        if (headers == null) {
            headers = new HashMap<>(0);
        }

        MediaType mediaType = MediaType.parse(HTTP_FORM);
        RequestBody body = RequestBody.create(mediaType, content);

        Request.Builder builder = new Request.Builder().url(url);
        headers.forEach(builder::addHeader);
        Request request = builder.post(body).build();

        return executeRequest(request);
    }

    /**
     * post with text
     * @param url
     * @param content
     * @return
     */
    public static String httpPostWithText(String url, String content) {
        return httpPostWithText(url, content, null);
    }

    /**
     * post with text
     * @param url
     * @param content
     * @param headers
     * @return
     */
    public static String httpPostWithText(String url, String content, Map<String, String> headers) {
        checkUrl(url);

        if (headers == null) {
            headers = new HashMap<>(0);
        }

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), content);
        Request.Builder builder = new Request.Builder().url(url);
        headers.forEach(builder::addHeader);
        Request request = builder.post(body).build();

        return executeRequest(request);
    }

    /**
     * check url
     * @param url
     */
    private static void checkUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            throw new InvalidUrlException(url);
        }
    }

    /**
     * execute request
     * @param request
     * @return
     */
    private static String executeRequest(Request request) {
        final int successCode = 200;
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            boolean success = Objects.equals(response.code(), successCode);
            if (success) {
                String content = response.body() != null ? response.body().string() : StringUtils.EMPTY;
                logger.info("http 请求成功; [{}, result = {}]", request, content);
                return content;
            } else {
                String content = response.body() != null ? response.body().string() : StringUtils.EMPTY;
                logger.warn("Http 请求失败; [{}, errorCode = {}, content = {}]", request, response.code(), content);
            }
        } catch (IOException e) {
            throw new HttpException("http 请求失败," + request, e);
        }
        return null;
    }

}
