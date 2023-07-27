package test.me;


import java.util.List;

public class Main {
    public static void main(String[] args) {
        //String sxxxx = "123";
        //char c = sxxxx.charAt(0);
        //System.out.println(c);

        List.of(1, 2, 3).forEach(System.out::println);

    }

    public void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

}