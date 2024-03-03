/**
 * 查分算法
 * eg leetcode 253会议室
 * */
package Difference;

import java.util.Arrays;
import java.util.Stack;

public class MinMeetingRooms2 {
    public static int minMeetingRooms2_1(int[][] intervals) {
        int length = intervals.length,max = 0,count =0;
        int[][] arr = new int[length *2][];
        for(int i=0;i<length;i++) {
            arr[i*2] = new int[]{intervals[i][0],1};
            arr[i*2+1] = new int[]{intervals[i][1],-1};
        }
        Arrays.sort(arr, (int[]interval1, int[]interval2) -> {
            return interval2[0] == interval1[0]
                    ? interval1[1] - interval2[1]
                    : interval1[0] - interval2[0];
        });
        for(int i=0;i<arr.length;i++) {
            count += arr[i][1];
            max = Math.max(max, count);
        }

        return max;
    }
    public static int minMeetingRooms2(int[][] intervals) {
        int length = intervals.length,max = 0;
        Arrays.sort(intervals, (int[] interval1, int[] interval2) -> {
            return interval2[0] ==  interval1[0]
                    ? interval1[1] - interval2[1]
                    : interval1[0] - interval2[0];
        });
        Stack<Integer> stack = new Stack<>();
        for(int i=0;i<intervals.length;i++) {
            while(!stack.isEmpty() && intervals[i][0] >= stack.peek()) {
                stack.pop();
            }
            stack.push(intervals[i][1]);
            max = Math.max(max, stack.size());
        }
        return max;
    }
    public static void main(String[] args) {
        System.out.println("Hello world!");
        int[][][] intervals = {{{0,30},{5,10},{10,15},{15,20}},{{7,10},{2,4}}};
        int[] res = {2,1};

        for(int i=0;i<intervals.length;i++) {
            System.out.println("\nTest: " + i);
            System.out.println("数组: ");
            for(int j=0;j<intervals[i].length;j++) {
                System.out.print(intervals[i][j] + " ");
            }
            int ret = minMeetingRooms2(intervals[i]);

            System.out.println("计算值： "+ ret + "   正确值: " + res[i] + "  是否相等： "+ (ret == res[i]));
        }
    }
}
