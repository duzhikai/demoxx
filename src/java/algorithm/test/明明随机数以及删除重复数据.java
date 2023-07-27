package algorithm.test;

import java.util.Arrays;

public class 明明随机数以及删除重复数据 {
    public static void main(String[] args) {
        //List<Integer> finalList = Arrays.asList(1, 2, 3);
        //ArrayList<Integer> list = new ArrayList<>(finalList);
        //for(int i = 0;i < list.size();i++){
        //    list.remove(i);
        //}
        //Iterator<Integer> iterator = list.iterator();
        //while (iterator.hasNext()) {
        //    iterator.next();
        //    iterator.remove();
        //}
        //System.out.println(list);
        int[] ints = {1, 2, 2, 3};
        int i = deleteRepeat(ints);
        System.out.println(Arrays.toString(ints));
        for (int j = 0; j <= i; j++) {
            System.out.println(ints[j]);
        }
    }
    public void sodaBottle(int num) {
        int sum = 0;
        while (num > 2) {
            sum += num / 3;
            num = num / 3 + num % 3;
        }
        if (num == 2) {
            sum++;
        }
        System.out.println(sum);
    }

    public static int deleteRepeat (int[] arr) {
        int i = 0;
        for (int j = 1; j < arr.length; j++) {
            if (arr[i] != arr[j]) {
                arr[++i] = arr[j];
            }
        }
        return i;
    }
}
