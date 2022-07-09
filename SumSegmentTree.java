package Array;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述
 *
 * @since 2022-07-09
 */
public class SumSegmentTree {
    public static void main(String[] args) {
        System.out.println("hello world!");
        SumSegmentTree tree = new SumSegmentTree(new int[]{6,5,2,3,4});
        System.out.println(tree.getRangeSum(0,2)); // 13
        System.out.println(tree.getRangeSum(0,1)); // 11
        System.out.println(tree.getRangeSum(3,4)); // 7
    }

    private List<Integer> minSegmentTree = new ArrayList<>();
    int n;
    public SumSegmentTree(int[] arr) {
        n = arr.length;
        for(int i=0;i<n;i++) {
            minSegmentTree.add(0);
        }
        for(int i=0;i<n;i++) {
            minSegmentTree.add(arr[i]);
        }
        for(int i=n-1;i>0;i--) {
            minSegmentTree.set(i, minSegmentTree.get(2*i)+ minSegmentTree.get(2*i+1));
        }
    }
    public void update(int i, int val) {
        i += n;
        minSegmentTree.set(i, val);
        while(i>1) {
            i =i>>1;
            minSegmentTree.set(i, minSegmentTree.get(2*i)+ minSegmentTree.get(2*i+1));
        }
    }
    public int getRangeSum(int start, int end) {
        int sum = 0;
        start += n;
        end += n;
        while(start < end) {
            if((start & 1) == 1) {
                sum += minSegmentTree.get(start);
                start++;
            }
            if((end & 1) == 0) {
                sum += minSegmentTree.get(end);
                end--;
            }
            start = start>>1;
            end = end>>1;
        }
        if(start == end) {
            sum += minSegmentTree.get(start);
        }

        return sum;
    }
}
