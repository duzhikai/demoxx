package huawei;

import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.util.*;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Demo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            String a = in.nextLine();
            String b = in.nextLine();
            String[] arr = b.split(" ");
            int[] ints = Arrays.stream(arr).mapToInt(Integer::parseInt).toArray();
            test(ints,0,0);
            LinkedList<Integer> maxList = Collections.max(res, Comparator.comparingInt(LinkedList::size));
            System.out.println(maxList.size());
            System.out.println(res.size());
            //System.out.println(res);
        }
    }
    static LinkedList<LinkedList<Integer>> res = new LinkedList<>();
    static LinkedList<Integer> trackList = new LinkedList<>();
    public static void test(int[] arr, int curr, int start) {
        if (start == arr.length) {
            res.add(new LinkedList<>(trackList));
            return;
        }
        for (int i = start; i < arr.length; i++) {
            if (curr < arr[i]) {
                trackList.add(arr[i]);
                test(arr, arr[i], i + 1);
                trackList.removeLast();
            }
            if (i == arr.length - 1) {
                res.add(new LinkedList<>(trackList));
                return;
            }
        }
    }

    public static void test1(int[] arr, int curr, int start) {
        if (start == arr.length) {
            return;
        }
        for (int i = start; i < arr.length; i++) {
            if (curr < arr[i]) {
                trackList.add(arr[i]);
                res.add(new LinkedList<>(trackList));
                test1(arr, arr[i], i + 1);
                trackList.removeLast();
            }
        }
    }

}
