/**
 * Given n non-nagative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.
 */

package Monotonic_stack;

import java.util.Stack;

public class LargestRectangleArea {

    public static int LargestRectangleArea (int[] nums) {
        int len = nums.length;
        int res = 0;
        int left[] = stepsToLeftFarestSmallerNumber(nums);
        int right[] = stepsToRightFarestSmallerNumber(nums);
        for(int i=0;i<len;i++) {
            System.out.println(nums[i]+ ": "+right[i]+ " "+ left[i]);
            res = Math.max(res, (right[i]+left[i]+1) * nums[i]);
        }
        return res;
    }
    public static int[] stepsToRightFarestSmallerNumber(int[] nums) {
        int len = nums.length;
        int[] res = new int[len];
        Stack<Integer> stack = new Stack<>();

        for(int i=len-1;i>=0;i--) {
            while(!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
                res[i] = stack.peek() - i + res[stack.peek()];
                stack.pop();
            }
            if(!stack.isEmpty() && nums[i] == nums[stack.peek()]) {
                res[i] = stack.peek() - i + res[stack.peek()];
            } else {
                stack.push(i);
            }

        }
        return res;
    }
    public static int[] stepsToLeftFarestSmallerNumber(int[] nums) {
        int len = nums.length;
        int[] res = new int[len];
        Stack<Integer> stack = new Stack<> ();

        for(int i=0;i<len;i++) {
            while(!stack.isEmpty() && nums[i] < nums[stack.peek()]) {
                res[i] = i - stack.peek() + res[stack.peek()];
                stack.pop();
            }
            if(!stack.isEmpty() && nums[i] == nums[stack.peek()]) {
                res[i] = i - stack.peek() + res[stack.peek()];
            } else {
                stack.push(i);
            }
        }
        return res;
    }
    public static void main(String[] args) {
        int[] arr = {2,1,5,6,2,3};
        int[] arr1 = {1,1};
        int[] arr2 = {999,999,999,999};
        int[] arr3 = {1,2,3,4,5};
        int[][] arrs = {{2,1,5,6,2,3},{999,999,999,999},{1,2,3,4,5},{5,4,3,2,1}};
        int[] ans = {10,999*4,9,9};
        for(int i=0;i<ans.length;i++) {
            int res = LargestRectangleArea((arrs[i]));
            System.out.println("i: "+ i + ", " + (res == ans[i]));
        }

    }
}

