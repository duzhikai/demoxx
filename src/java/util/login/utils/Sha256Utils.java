package util.login.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//技术点 :sha256加密工具

public class Sha256Utils {
    //使用sha256加密明文密码

    public static String getSha256Str(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
//1得到一位的进行补0操作
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }



    public static void main(String[] args) {
        //明文密码和盐拼接的字符串
//        Yy1234567

        String salted = "892efd42ae8e056f2c128e9c36554401";
        //数据库密码
        String dbPassword = "87020f154686b29e3157de9dfc80a681f89b56c8843f8513a778aa5ecc43c39a";

        //加密
        String password = Sha256Utils.getSha256Str(salted);
        System.out.println(password);
        //true
    }
}