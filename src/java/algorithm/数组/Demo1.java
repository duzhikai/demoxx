package algorithm.数组;

public class Demo1 {
    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 2, 2, 3};
        int i = removeDuplicates(nums, 2);
        System.out.println(i);
    }

    static int removeDuplicates(int[] nums, int k) {
        int idx = 0;
        for (int x : nums) {
            if (idx < k || nums[idx - k] != x) nums[idx++] = x;
        }
        return idx;
    }

}
