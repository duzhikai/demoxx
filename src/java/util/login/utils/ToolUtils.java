package util.login.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.math.BigDecimal.ROUND_DOWN;

public class ToolUtils {

    /**
     * 金额转换 分 ==> 元
     *
     * @param money
     * @return
     */
    public static String moneyFormatYuan(BigDecimal money) {
        if (Objects.equals(money, BigDecimal.ZERO)) {
            return "0";
        }

        BigDecimal divide = money.divide(new BigDecimal(100), 2, RoundingMode.CEILING);
        return divide.toPlainString();
    }

    public static String getShortRandKey() {
        return getShortRandKey(5);
    }

    public static String getShortRandKey(int key_len) {
        String randomKey = MD5Util.getRandomKey();
        int len = randomKey.length() - 1;
        String randStr = "";
        for (int i = 0; i < key_len; i++) {
            int index = RandomUtil.randomInt(0, len);
            randStr += randomKey.charAt(index);
        }
        return randStr;
    }

    /**
     * 创建开发者订单
     *
     * @return string
     */
    public static String buildOrder() {

        long current = DateUtil.current();
        String substring = String.valueOf(current).substring(7, 13);
        String[] s = substring.split("");
        String str = "";
        for (String item : s) {
            str = str + stringToAscii(item);
        }
        String date = DateUtil.format(new Date(), "yyyyMMdd");
        return date + str.substring(0, 8);
    }


    public static String stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }


    /**
     * 验证是否是json格式
     *
     * @param str
     * @return true: 是，false不是
     */
    public static boolean getJSONType(String str) {
        boolean result = false;
        if (StringUtils.isNotBlank(str)) {
            str = str.trim();
            if (str.startsWith("{") && str.endsWith("}")) {
                result = true;
            } else if (str.startsWith("[") && str.endsWith("]")) {
                result = true;
            }
        }
        return result;
    }


    /**
     * NOTE: 解析url中的参数和值
     *
     * @param query
     * @return array
     * <p>
     * 用法：
     * $url = 'http://renwu.cloudin.com/user-task/package-task-list?package_id=27561&task_key=169e4c373e1879a9fa6d15330d4b4347';
     * $query_data = parse_url($url);
     * $query_arr = Tool::convertUrlQuery($query_data)
     */
    public static Map<String, String> convertUrlQuery(String query) {

        Map<String, String> params = Maps.newHashMap();
        String[] queryParts = query.split("&", -1);
        for (String param : queryParts) {
            String[] item = param.split("=", -1);
            if (item.length > 1) {
                params.put(item[0], item[1]);
            }
        }

        return params;
    }

    /**
     * 函数搜索字符串在另一字符串中是否存在，如果是，返回该字符串及剩余部分，否则返回 null
     *
     * @param str1
     * @param str2
     * @param before_needle: true:返回匹配到当前字符串前的部分，不包含自己，false当前字符串的后部分，包含自己
     * @return
     */
    public static String strStr(String str1, String str2, boolean before_needle) {
        if (StringUtils.isEmpty(str1) || StringUtils.isEmpty(str2)) {
            return null;
        }

        int index = str1.indexOf(str2);
        if (before_needle) {
            return StrUtil.sub(str1, 0, index);
        } else {
            return StrUtil.sub(str1, index, str1.length());
        }
    }

    /**
     * 获取上传文件文件名称，不包含. ，例如上传的是aaa.csv,返回aaa
     *
     * @param multipartFile
     * @return
     */
    public static String getUploadFileName(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename().substring(0, multipartFile.getOriginalFilename().lastIndexOf("."));
    }

    public static String getUploadFileName(File file) {
        return file.getName().substring(0, file.getName().lastIndexOf("."));
    }

    /**
     * 获取上传文件后缀，例如上传的是aaa.csv,返回.csv
     *
     * @param multipartFile
     * @return
     */
    public static String getUploadFileType(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
    }

    public static String getUploadFileType(File file) {
        return file.getName().substring(file.getName().lastIndexOf("."));
    }

    /**
     * 获取新拼接的文件名
     *
     * @param baseName 基础名字
     * @param fileType 文件类型（.csv）
     * @return
     */
    public static String getNewFileName(String baseName, String fileType) {
        return getNewFileName(baseName, null, fileType);
    }

    /**
     * 获取新拼接的文件名
     *
     * @param baseName 基础名字
     * @param fileType 文件类型（.csv）
     * @param nameExt  文件名扩展名称
     * @return
     */
    public static String getNewFileName(String baseName, String nameExt, String fileType) {
        if (StringUtils.isEmpty(nameExt)) {
            return baseName + "_" + ToolUtils.getShortRandKey(5) + fileType;
        }
        return baseName + "_" + ToolUtils.getShortRandKey(5) + nameExt + fileType;
    }

    /**
     * 获取文件扩展名
     *
     * @param file_name
     * @return
     */
    public static String getExtension(String file_name) {
        if (!file_name.contains(".")) {
            return null;
        }
        int index = file_name.lastIndexOf(".");
        return StrUtil.sub(file_name, index + 1, file_name.length());
    }

    /**
     * 判断是否是base64字符串
     *
     * @param str 检测字符串
     * @return 是否是base64字符串
     */
    public static boolean isBase64(String str) {
        if (null == str) {
            return false;
        }
        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        if (!Pattern.matches(base64Pattern, str)) {
            return false;
        }

        String regex = "^[0-9]*$";
        if (str.matches(regex)) {
            return false;
        } else if (!"".equals(base64Decode(str))) {
            return true;
        }

        return false;
    }

    public static String base64Decode(String str) {
        if (null == str) {
            return null;
        }
        Base64.Decoder decoder = Base64.getDecoder();
        final byte[] textByte = str.getBytes(StandardCharsets.UTF_8);

        return new String(decoder.decode(textByte));
    }

    public static String base64Encode(String str) {
        if (null == str) {
            return null;
        }
        Base64.Encoder encoder = Base64.getEncoder();
        final byte[] textByte = str.getBytes(StandardCharsets.UTF_8);

        return encoder.encodeToString(textByte);
    }

    /**
     * NOTE: 加密方法
     * User: hjl
     *
     * @param data
     * @param key
     * @return string
     */
    public static String encrypt(String data, String key) {
//        return AESUtil.encrypt(data, key);
        // 重构后的加密算法与原php版本算法不一致，导致前端对应解密算法失效，暂时透传出去
        return data;
    }

    /**
     * NOTE: 加密方法
     *
     * @param data
     * @param key
     * @return string
     */
    public static String decrypt(String data, String key) {
//        return AESUtil.decrypt(data, key);
        // 重构后的加密算法与原php版本算法不一致，导致前端对应解密算法失效，暂时透传出去
        return data;
    }

    public static String csvJsonDecode(String str) {
        return StrUtil.replace(str, "||", ",");
    }

    public static Double ceil(Integer a,Integer b){
        if (a == 0 || b == 0){
            return 0d;
        }
        float c = (float) a/(float)b * 100;
        BigDecimal two = new BigDecimal(c);
        return two.setScale(2,ROUND_DOWN).doubleValue();
    }
}
