package Interview;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Demo1 {
    /*
    * 二分查找、快排、归并排序、堆排序
    * 二叉树的中序遍历、层序遍历、最大深度
    * 有效的括号：栈解决，遇到左括号，就进栈，遇到右括号，就出栈，出的时候判断对错。LC
    * 合并两个有序链表
    * 删除有序数组中的重复项
    * 在排序数组中查找元素的第一个和最后一个位置
    * 全排列
    * 爬楼梯
    * 验证回文串
    * 最长连续序列
    * 相交链表
    * 反转链表
    * 回文链表
    *
    * */
    public static void main(String[] args) {
        int[] ints = add2();
        Arrays.stream(ints).forEach(System.out::println);
    }

    private static int[] add2() {
        List<Integer> list = Arrays.asList(2, 7, 11, 15);
        HashMap<Integer, Integer> map = new HashMap<>();
        int target = 13;
        for (int i = 0; i < list.size(); i++) {
            if (map.containsKey(target-list.get(i))) {
                return new int[]{map.get(target - list.get(i)), i};
            } else {
                map.put(list.get(i),i);
            }
        }
        return null;
    }
}
