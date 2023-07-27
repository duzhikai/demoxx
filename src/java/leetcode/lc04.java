package leetcode;

import java.util.Arrays;

/**
 * lc04: 寻找两个正序数组的中位数
 *
 * @author zhikai.du
 * @date 2023/05/17
 */
public class lc04 {

}

class solution{
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len=nums1.length+nums2.length;
        int [] res=new int[len];
        for(int i=0;i<nums1.length;i++)
            res[i]=nums1[i];
        for(int j=0;j<nums2.length;j++)
            res[j+nums1.length]=nums2[j];
        Arrays.sort(res);//将数组进行排序
        if(len%2==0)
            return (res[len/2]+res[len/2-1])/2.0;
        else
            return res[len/2];
    }
}
