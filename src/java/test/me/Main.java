package test.me;

import java.util.Scanner;
import java.util.Arrays;
 public class Main {
    public static void main(String[] args) {
        String tb = "345678910JQKA2jokerJOKER";
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            int idx = s.indexOf('-');
            String t1 = s.substring(0, idx);
            String t2 = s.substring(idx + 1);
            int c1 = countSpaces(t1);
            int c2 = countSpaces(t2);
            if (c1 != c2) {
                if (t1.equals("joker JOKER") || t2.equals("joker JOKER")) {
                    System.out.println("joker JOKER");
                } else if (c1 == 3) {
                    System.out.println(t1);
                } else if (c2 == 3) {
                    System.out.println(t2);
                } else {
                    System.out.println("ERROR");
                }
            } else {
                String s1 = t1 + ' ';
                String s2 = t2 + ' ';
                s1 = s1.substring(0, s1.indexOf(' '));
                s2 = s2.substring(0, s2.indexOf(' '));
                 int i1 = tb.indexOf(s1);
                int i2 = tb.indexOf(s2);
                if (i1 > i2) {
                    System.out.println(t1);
                } else {
                    System.out.println(t2);
                }
            }
        }
    }
     public static int countSpaces(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                count++;
            }
        }
        return count;
    }
}