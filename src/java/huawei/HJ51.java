package huawei;

/**
 * hj51 : 输出单向链表中倒数第k个结点
 *
 * @author zhikai.du
 * @date 2023/05/12
 */
public class HJ51 {
    static class ListNode {
        int val;
        ListNode next = null;

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode findKthToTail(ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }

        ListNode fast = head;
        ListNode slow = head;

        // fast先走k步
        for (int i = 0; i < k; i++) {
            if (fast == null) {
                return null;
            }

            fast = fast.next;
        }

        // fast和slow一起走，直到fast走到尾部
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }

    public static void main(String[] args) {
        char ch = '1';
        int n =(char)(ch-'0');
        System.out.println(n);
    }

}
