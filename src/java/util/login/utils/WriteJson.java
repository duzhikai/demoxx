package util.login.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class WriteJson {

    /**
     * 以JSON格式输出
     * @param response
     */
    public static void writeJsonToPage(String json, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(json);
        } catch (IOException e) {
            log.error("error", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
