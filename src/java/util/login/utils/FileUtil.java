package util.login.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FileUtil {

    /**
     * 下载文件
     * @param fileUrl
     * @return
     */
    public static InputStream downLoadFile(String fileUrl) {
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/csv");
        // 获得文件流
        InputStream inputStream = OkHttpUtils.httpGetFile(fileUrl, header);
        log.info("{}文件下载成功", fileUrl);
        return inputStream;
    }
}
