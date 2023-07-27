package algorithm.test;

import java.util.List;

public class Demo3 {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    static ListNode head = new ListNode(-1);

    public static void main(String[] args) {
        ListNode head = new ListNode(-1);
        ListNode head1 = new ListNode(1);
        ListNode head2 = new ListNode(2);
        ListNode head3 = new ListNode(3);
        ListNode head4 = new ListNode(4);
        head.next = head1;
        head1.next = head2;
        head2.next = head3;
        head3.next = head4;
        getNodeByDescIndex(head, 3);
        getNodeByDescIndex1(head, 3);

    }

    public static void getNodeByIndex (ListNode head, int index) {
        int i = -1;
        ListNode p = head.next;
        while (p != null) {
            i++;
            if (i == index) {
                System.out.println(p.val);
            }
            p = p.next;
        }
        
    }
    static int i = 0;
    public static void getNodeByDescIndex (ListNode node, int index) {
        if (node == null) {
            return;
        }
        getNodeByDescIndex(node.next, index);
        i++;
        if (i == index) {
            System.out.println(node.val);
        }
    }

    public static void getNodeByDescIndex1 (ListNode node, int k) {
        // 定位快慢指针
        ListNode slow = node;
        ListNode fast = slow;
        while (k > 0) {
            fast = fast.next;
            k--;
        }
        // 遍历倒数k个节点
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        System.out.println(slow.val);
    }
}
