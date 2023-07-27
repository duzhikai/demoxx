package huawei;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class 梅花桩 {


    /**
     * 求最长递增子序列
     * @param arr
     * @return
     */

    static int maxDistance(int[] arr) {
        int max = 0;
        int[] dp = new int[arr.length];
        dp[0] = 1;
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            while (j >= 0 && arr[j] < arr[i]) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
                j--;
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
    /**
     * 梅花桩
     * 输入：
     * 6
     * 2 5 1 5 4 5
     * 输出：
     * 3
     */
    static LinkedList<LinkedList<Integer>> res = new LinkedList<>();
    static LinkedList<Integer> trackList = new LinkedList<>();
    public static void test(int[] arr, int curr, int start) {
        if (start == arr.length) {
            return;
        }
        for (int i = start; i < arr.length; i++) {
            if (curr < arr[i]) {
                trackList.add(arr[i]);
                if (i == arr.length - 1) {
                    res.add(new LinkedList<>(trackList));
                }
                test(arr, arr[i], i+1);
                trackList.removeLast();
            }
        }
    }
    public static void main(String[] args) {
        //int[] arr = {2, 5, 1, 5, 4, 5};
        //int i = maxDistance(arr);
        //System.out.println(i);

        //int[] arr = {2, 5, 1, 5, 4, 5};
        //int[] arr = {2, 5, 1, 5, 4, 5};
        //test(arr, 0, 0);
        //System.out.println(res);
        //LinkedList<Integer> max = Collections.max(res, (o1, o2) -> o1.size() - o2.size());
        //LinkedList<Integer> min = Collections.max(res, (o1, o2) -> o2.size() - o1.size());
        //System.out.println(max);
        //System.out.println(min);


        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            String a = in.nextLine();
            for (int i = 0; i < Integer.parseInt(a); i++) {
                String b = in.nextLine();
                String[] arr = b.split(" ");
                int[] ints = Arrays.stream(arr).mapToInt(Integer::parseInt).toArray();
                test(ints,0,0);
                LinkedList<Integer> maxList = Collections.max(res, Comparator.comparingInt(LinkedList::size));
                System.out.println(maxList.size());
            }
        }
    }
}
