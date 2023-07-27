package algorithm.递归;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

public class 倒序层级遍历二叉树 {

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(null, 1, null);
        TreeNode treeNode1 = new TreeNode(null, 2, null);
        TreeNode treeNode2 = new TreeNode(null, 3, null);
        TreeNode treeNode3 = new TreeNode(null, 4, null);
        TreeNode treeNode4 = new TreeNode(null, 5, null);

        treeNode.left = treeNode1;
        treeNode.right = treeNode2;
        treeNode1.left = treeNode3;
        treeNode3.left = treeNode4;
        levelOrderBottom(treeNode);

    }
    static class TreeNode {
        private TreeNode left;
        private int val;
        private TreeNode right;

        public TreeNode(TreeNode left, int val, TreeNode right) {
            this.left = left;
            this.val = val;
            this.right = right;
        }
    }

    public static List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        helper(res, root, 0);
        return res;
    }

    public static void helper(List<List<Integer>> list, TreeNode root, int level) {
        //边界条件判断
        if (root == null)
            return;
        //如果level等于list的长度，说明到下一层了，
        //并且下一层的ArrayList还没有初始化，我们要
        //先初始化一个ArrayList，然后放进去。
        if (level == list.size()) {
            list.add(0, new ArrayList<>());
        }
        //这里就相当于从后往前打印了
        list.get(list.size() - level - 1).add(root.val);
        //当前节点访问完之后，再使用递归的方式分别访问当前节点的左右子节点
        helper(list, root.left, level + 1);
        helper(list, root.right, level + 1);
    }
}
