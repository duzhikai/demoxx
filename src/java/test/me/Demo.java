package test.me;

import java.util.Scanner;
import java.util.HashMap;
public class Demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine().trim();
        String s = sc.nextLine().trim();
        int left = 0, ans = 0;
        HashMap<Character, Integer> hashMap = new HashMap<>(); // 哈希表记录窗口中各个字符出现频数
        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            if (ch != a.charAt(0)) {              // ch不为应该排除的数：进行滑窗
                hashMap.put(ch, hashMap.getOrDefault(ch, 0) + 1);    // A1
                while (hashMap.get(ch) == 3) {             // A2
                    hashMap.put(s.charAt(left), hashMap.get(s.charAt(left)) - 1);
                    left++;
                }
                ans = Math.max(ans, right - left + 1);                      // A3
            } else {                                   // ch为应该排除的数：滑窗清空，从right+1开始下一个滑窗
                left = right + 1;
                hashMap.clear();
            }
        }
        System.out.println(ans);
    }
}
