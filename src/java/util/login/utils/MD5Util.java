package util.login.utils;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class MD5Util {
    /**
     *
     * @param str
     * @return
     */
    public static String encryptToMD5(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String getRandomKey(){
        Random random = new Random();
        int randNumber = random.nextInt(1000000000);
        String s = System.currentTimeMillis() / 1000 + String.valueOf(randNumber);
        return DigestUtils.md5Hex(s);
    }



    public static void main(String[] args) {
        String plainText = "123456";//明文
        String saltValue = "8";//盐值
    }
}

