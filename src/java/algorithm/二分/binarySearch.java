package algorithm.二分;

public class binarySearch {
    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 6, 7, 8, 9, 10};
        int target = 5;
        int index = binarySearch(arr, target);
        System.out.println(index);
    }



    /*
    * 这里如果是带符号的二进制，最高位为1，说明是负数，负数的二进制转十进制，需要取反再加1。所以是-1
    * */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length;
        int mid = 0;
        while (left <= right) {
            mid = (left + right) >>> 2;
            if (target == arr[mid]) {
                return mid;
            } else if (target > arr[mid]) {
                left = mid + 1;
            } else if (target < arr[mid]) {
                right = mid - 1;
            }
        }
        return -1;
    }
}
