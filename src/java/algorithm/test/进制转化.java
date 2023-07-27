package algorithm.test;

import java.util.HashMap;

/**
 * 各进制转化: https://blog.csdn.net/weixin_54029352/article/details/126480816
 */
public class 进制转化 {

    private static final StringBuilder sbFinal = new StringBuilder();
    public static final HashMap<String, String> map = new HashMap<>();
    static {
        map.put("10", "A");
        map.put("11", "B");
        map.put("12", "C");
        map.put("13", "D");
        map.put("14", "E");
        map.put("15", "F");
    }
    public static void main(String[] args) {
        //StringBuilder s = conversion(1000, 16, new StringBuilder());
        //System.out.println(s.reverse().toString());
        //conversion2(10,2);
        //System.out.println(sbFinal.reverse().toString());
        int i = ConvertToDecimal("0x1A");
        System.out.println(i);
    }

    public static int ConvertToDecimal (String str) {
        int hex = 0;
        if (str.startsWith("0x")) {
            hex = 16;
            str = str.substring(2);
        } else if (str.startsWith("0b")) {
            hex = 2;
            str = str.substring(2);
        } else if (str.startsWith("0")) {
            hex = 8;
            str = str.substring(1);
        } else {
            hex = 10;
        }

        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            if (hex == 16) {
                if (str.charAt(i) >= 'A' && str.charAt(i) <= 'F') {
                    sum += (str.charAt(i) - 'A' + 10) * Math.pow(hex, str.length() - i - 1);
                } else {
                    sum += Integer.parseInt(String.valueOf(str.charAt(i))) * Math.pow(hex, str.length() - i - 1);
                }
            }else {
                sum += Integer.parseInt(String.valueOf(str.charAt(i))) * Math.pow(hex, str.length() - i - 1);
            }
        }
        return sum;
    }

    public static StringBuilder conversion(int num, int hex, StringBuilder sb){
        if (num == 0) {
            return null;
        }
        int quotient = num / hex;
        int remainder = num % hex;
        if (remainder > 9) {
            sb.append(map.get(String.valueOf(remainder)));
        } else {
            sb.append(remainder);
        }
        conversion(quotient, hex, sb);
        return sb;
    }

    public static void conversion2 (int num, int hex) {
        if (num == 0) {
            return;
        }
        int quotient = num / hex;
        int remainder = num % hex;
        if (remainder > 9) {
            sbFinal.append(map.get(String.valueOf(remainder)));
        } else {
            sbFinal.append(remainder);
        }
        conversion2(quotient, hex);
    }
}
