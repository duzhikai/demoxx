package util.login.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OkHttpUtils {
    private static final String HTTP_JSON = "application/json; charset=utf-8";
    private static final String HTTP_FORM = "application/x-www-form-urlencoded; charset=utf-8";

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build();


    /**
     * get请求
     * 对于小文档，响应体上的string()方法非常方便和高效。
     * 但是，如果响应主体很大(大于1 MB)，则应避免string()，
     * 因为它会将整个文档加载到内存中。在这种情况下，将主体处理为流。
     *
     * @param url
     * @return
     */
    public static String httpGet(String url) {
        if (url == null || "".equals(url)) {
            log.error("url为null!");
            return "";
        }

        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("http GET 请求成功; [url={}]", url);
                return response.body().string();
            } else {
                log.warn("Http GET 请求失败; [errorCode = {} , url={}]", response.code(), url);
            }
        } catch (IOException e) {
            throw new RuntimeException("同步http GET 请求失败,url:" + url, e);
        }
        return null;
    }

    public static String httpGet(String url, Map<String, String> headers) {
        if (ObjectUtils.isEmpty(headers)) {
            return httpGet(url);
        }

        Request.Builder builder = new Request.Builder();
        headers.forEach(builder::header);
        Request request = builder.get().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("http GET 请求成功; [url={}]", url);
                return response.body().string();
            } else {
                log.info("Http GET 请求失败; [errorxxCode = {} , url={}]", response.code(), url);
            }
        } catch (IOException e) {
            throw new RuntimeException("同步http GET 请求失败,url:" + url, e);
        }
        return null;
    }

    public static InputStream httpGetFile(String url, Map<String, String> headers) {
        if (ObjectUtils.isEmpty(headers)) {
            log.info("http Get 下载文件请求头为空!");
            throw new RuntimeException("http请求头为空!");
        }

        Request.Builder builder = new Request.Builder();
        headers.forEach(builder::header);
        Request request = builder.get().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("http GET 请求成功; [url={}]", url);
                return response.body().byteStream();
            } else {
                log.info("Http GET 请求失败; [errorxxCode = {} , url={}]", response.code(), url);
            }
        } catch (IOException e) {
            throw new RuntimeException("同步http GET 请求失败,url:" + url, e);
        }
        return null;
    }

    /**
     * 同步 POST调用 无Header
     *
     * @param url
     * @param json
     * @return
     */
    public static String httpPostJson(String url, String json) {
        if (url == null || "".equals(url)) {
            log.error("url为null!");
            return "";
        }

        MediaType JSON = MediaType.parse(HTTP_JSON);
        RequestBody body = RequestBody.create(JSON, json);
        try {
            Request.Builder requestBuilder = new Request.Builder().url(url);
            Request request = requestBuilder.post(body).build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("http Post 请求成功; [url={}, requestContent={}]", url, json);
                return response.body().string();
            } else {
                log.error("Http POST 请求失败; [ errorCode = {}, url={}, param={}]", response.code(), url, json);
            }
        } catch (IOException e) {
            log.error("回调地址{},请求失败！param:{}", url, json);
        }
        return null;
    }

    /**
     * 同步 POST调用 有Header
     *
     * @param url
     * @param headers
     * @param json
     * @return
     */
    public static String httpPostJson(String url, Map<String, String> headers, String json) {
        if (ObjectUtils.isEmpty(headers)) {
            httpPostJson(url, json);
        }

        MediaType JSON = MediaType.parse(HTTP_JSON);
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        headers.forEach(requestBuilder::addHeader);
        Request request = requestBuilder.post(body).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("http Post 请求成功; [url={}, requestContent={}]", url, json);
                return response.body().string();
            } else {
                log.warn("Http POST 请求失败; [ errorCode = {}, url={}, param={}]", response.code(), url, json);
            }
        } catch (IOException e) {
            throw new RuntimeException("同步http请求失败,url:" + url, e);
        }
        return null;
    }

    /**
     * 提交表单
     * @param url
     * @param content
     * @param headers
     * @return
     */
    public static String postDataByForm(String url, String content, Map<String, String> headers) {
        MediaType mediaType = MediaType.parse(HTTP_FORM);
        RequestBody body = RequestBody.create(mediaType, content);

        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null && headers.size() > 0) {
            headers.forEach(requestBuilder::addHeader);
        }
        Request request = requestBuilder
                .post(body)
                .build();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("postDataByForm; [postUrl={}, requestContent={}, response={}]", url, content);
                return response.body().string();
            } else {
                log.info("Http Post Form请求失败,[url={}, param={}]", url, content);
            }
        } catch (IOException e) {
            log.error("Http Post Form请求失败,[url={}, param={}]", url, content, e);
            throw new RuntimeException("Http Post Form请求失败,url:" + url);
        }
        return null;
    }

    public static String getCookie(HttpServletRequest request, String key){
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(key)){
                    return cookie.getValue();
                }
            }
        }
        return  null;
    }
}
