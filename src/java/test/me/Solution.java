package test.me;

import java.util.*;
 public class Solution {
    // 定义一个节点类，包含该节点的val、parent和children列表
    static class TreeNode {
        int val;
        int parent;
        List<Integer> children;
         public TreeNode(int parent, int val) {
            this.parent = parent;
            this.val = val;
            children = new ArrayList<>();
        }
    }
    static int n;           // n代表树中节点的个数
    static TreeNode[] nodes;   // nodes数组用于保存每个节点的信息
    static int ans = Integer.MIN_VALUE;    // ans用于记录最终答案
     public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        nodes = new TreeNode[n];
         // 逐一输入每个节点的信息
        for (int i = 0; i < n; i++) {
            int idx = sc.nextInt();     // 节点编号
            int parent = sc.nextInt();  // 父节点编号
            int val = sc.nextInt();     // val值
             // 构建节点
            nodes[idx] = new TreeNode(parent, val);
            if (parent != -1) {
                // 如果不是根节点，将当前节点添加到它的父节点的children列表中
                nodes[parent].children.add(idx);
            }
        }
         // 找到根节点root
        int root = 0;
        for (int i = 0; i < n; i++) {
            if (nodes[i].parent == -1) {
                root = i;
                break;
            }
        }
         // 调用dfs()函数，入口为根节点root，根节点的preMax为0，即前面没有任何路径
        dfs(root, 0);
        System.out.println(ans);
    }
     // dfs()函数中包含两个参数：
    // curNode表示【当前节点】
    // preMax 表示【以当前节点的父节点为结尾的某条路径】的最大和
    private static void dfs(int curNode, int preMax) {
        // 两种情况：
        // 1. 如果之前路径最大和是个负数，那么不再考虑前面的路径，应该从当前节点重新出发，
        // 即 curMax = nodes[curNode].val
        // 2. 如果之前路径最大和是个正数，那么以当前节点为结尾的路径的最大和应该加上之前路径最大和，
        // 即 curMax = nodes[curNode].val + preMax
        int curMax = preMax < 0 ? nodes[curNode].val : nodes[curNode].val + preMax;
         // 计算了当前路径最大和之后，更新答案ans
        ans = Math.max(ans, curMax);
         // 如果当前节点curNode不是任何节点的父节点，表示已经到达路径末端，终止递归
        if (nodes[curNode].children.size() == 0) {
            return;
        }
         // 如果仍有向下走的可能性
        for (int nxtNode : nodes[curNode].children) {
            // 对于下一个节点nxtNode而言，curNode的curMax就是它的preMax
            dfs(nxtNode, curMax);
        }
    }
}