package huawei;// 导入需要用到的库
import java.util.*;
 public class equalSubArraysMin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int ans = 0; // 保存最终结果，最小值
        int total = 0, tmp = 0;
        Vector<Integer> vec = new Vector<Integer>();
        boolean[] bFlag = new boolean[100]; // 记录当前数据是否被使用过
        boolean bGetAns = true;
         for (int i = 0; i < m; ++i) {
            tmp = scanner.nextInt();
            vec.add(tmp);
        }
        Collections.sort(vec);
         ans = Collections.max(vec);
        total = vec.stream().reduce(0, Integer::sum);
         for (ans++; ans < total; ans++) {
            if (total % ans == 0) {
                bGetAns = true;
                Arrays.fill(bFlag, false);
                for (int i = m - 1; i >= 0; --i) {
                    if (bFlag[i]) {
                        continue;
                    }
                    int tmptotal = vec.elementAt(i);
                    bFlag[i] = true;
                    for (int j = 0; j < i; ++j) {
                        if (bFlag[j]) {
                            continue;
                        }
                        if (tmptotal + vec.elementAt(j) < ans) {
                            tmptotal += vec.elementAt(j);
                            bFlag[j] = true;
                        } else if (tmptotal + vec.elementAt(j) == ans) {
                            tmptotal += vec.elementAt(j);
                            bFlag[j] = true;
                            break;
                        }
                    }
                    if (tmptotal != ans) {
                        bGetAns = false;
                        break;
                    }
                }
                if (bGetAns) {
                    break;
                }
            }
        }
         System.out.println(ans);
        scanner.close();
    }
}