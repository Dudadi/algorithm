/**
 * preSum：解决连续子数组求和问题。leetcode 560 给定一个整数数组和一个整数k，
 * 你需要找到该数组中和为k的连续的子数组的个数。
 * */

package PrefixSum;

import java.util.HashMap;
import java.util.Map;

public class SubArraySum {
    public static int subarraysum1(int[] nums, int k) {
        int count =0,length = nums.length;
        for(int i = 0;i< length;i++) {
            int sum =0;
            for(int j=i;j<length;j++) {
                sum += nums[j];
                if(sum ==k) {
                    count += 1;
                }
            }
        }
        return count;
    }
    public static int subarraysum(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        int count =0, sum =0, length = nums.length;
        map.put(0,1);
        for(int i=0;i<length;i++) {
            sum += nums[i];
            if(map.get(sum-k) != null) {
                count += map.get(sum-k);
            }
            map.put(sum,map.getOrDefault(sum,0)+1);
        }
        return count;
    }
    public static void main(String[] args) {
        System.out.println("Hello world!");
        int[][] nums = {{1,1,1},{0},{1,2,3,4,5},{-1,-1,1}};
        int[] ks = {2,1,5,1};
        int[] res = {2,0,2,1};
        for(int i=0;i<nums.length;i++) {
            System.out.println("\nTest: " + i);
            System.out.println("数组: ");
            for(int j=0;j<nums[i].length;j++) {
                System.out.print(nums[i][j] + " ");
            }
            System.out.println("\n K: "+ ks[i]);
            int ret = subarraysum(nums[i],ks[i]);

            System.out.println("计算值： "+ ret + "   正确值: " + res[i] + "  是否相等： "+ (ret == res[i]));
        }
    }
}
