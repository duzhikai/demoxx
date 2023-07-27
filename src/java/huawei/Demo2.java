package huawei;

import java.util.Stack;

public class Demo2 {
    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();
        String s = demo2.removeKdigits("123000", 3);
        System.out.println(s);
    }
    // 1219  1432219
        public String removeKdigits(String num, int k) {
            Stack<Character> stack = new Stack<Character>();
            int remain = num.length() - k;
            for (char digit : num.toCharArray()) {
                while (k > 0 && !stack.isEmpty() && stack.peek() > digit) {
                    stack.pop();
                    k -= 1;
                }
                stack.push(digit);
            }
            StringBuilder sb = new StringBuilder();
            for (char digit : stack.subList(0, remain)) {
                sb.append(digit);
            }
            String result = sb.toString().replaceFirst("^0*", "");
            return result.isEmpty() ? "0" : result;
        }
}
