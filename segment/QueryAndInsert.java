package segment;


import java.util.*;

/**
 * leetcode 3072. Distribute Elements Into Two Arrays II
 * You are given a 1-indexed array of integers nums of length n.
 *
 * We define a function greaterCount such that greaterCount(arr, val) returns the number of elements in arr that are strictly greater than val.
 *
 * You need to distribute all the elements of nums between two arrays arr1 and arr2 using n operations. In the first operation, append nums[1] to arr1. In the second operation, append nums[2] to arr2. Afterwards, in the ith operation:
 *
 * If greaterCount(arr1, nums[i]) > greaterCount(arr2, nums[i]), append nums[i] to arr1.
 * If greaterCount(arr1, nums[i]) < greaterCount(arr2, nums[i]), append nums[i] to arr2.
 * If greaterCount(arr1, nums[i]) == greaterCount(arr2, nums[i]), append nums[i] to the array with a lesser number of elements.
 * If there is still a tie, append nums[i] to arr1.
 * The array result is formed by concatenating the arrays arr1 and arr2. For example, if arr1 == [1,2,3] and arr2 == [4,5,6], then result = [1,2,3,4,5,6].
 *
 * Return the integer array result.
 * */
public class QueryAndInsert {
    public class SegmentTree {
        int[] arr;
        int n;

        public SegmentTree (int n) {
            this.n = n;
            this.arr = new int[2*n];
        }

        void update(int index, int val) {
            index += n;
            arr[index] += val;
            while(index > 1) {
                index = index / 2;
                arr[index] = arr[2*index] + arr[2*index+1];
            }
        }

        int query(int l, int r) {
            int res = 0;
            l += n;
            r += n;
            while(l <= r) {
                if((l & 1) == 1) {
                    res += arr[l];
                    l++;
                }
                if((r & 1) == 0) {
                    res += arr[r];
                    r--;
                }
                l >>= 1;
                r >>= 1;
            }
            return res;
        }
    }

    public int[] resultArray(int[] nums) {
        int n = nums.length;
        Set<Integer> set = new TreeSet<>();
        for(int num: nums) {
            set.add(num);
        }
        int size = set.size();
        int[] distinctArray = new int[size];
        int index = 0;
        for(int num: set) {
            distinctArray[index++] = num;
        }
        Arrays.sort(distinctArray);
        Map<Integer, Integer> indexMap = new HashMap<>();
        for(int i=0;i<size;i++) {
            indexMap.put(distinctArray[i], i);
        }
        SegmentTree t1 = new SegmentTree(size), t2 = new SegmentTree(size);
        List<Integer> l1 = new ArrayList<>(), l2 = new ArrayList<>();
        int index1 = 0, index2 = 0;
        l1.add(nums[0]);
        t1.update(index1, 1);
        l2.add(nums[1]);
        t2.update(index2, 1);

        for(int i=2;i<n;i++) {
            int curIndex = indexMap.get(nums[i]);
            int count1 = t1.query(curIndex + 1, size-1);
            int count2 = t2.query(curIndex + 1, size-1);
            if(count1 > count2 || (count1 == count2 && index1 <= index2)) {
                l1.add(nums[i]);
                t1.update(curIndex, 1);
                index1++;
            } else {
                l2.add(nums[i]);
                t2.update(curIndex, 1);
                index2++;
            }
        }
        int[] res = new int[n];
        int l1Size = l1.size();
        for(int i=0;i<n;i++) {
            if(i<l1Size) {
                res[i] = l1.get(i);
            } else {
                res[i] = l2.get(i-l1Size);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        QueryAndInsert obj = new QueryAndInsert();
        // [5,14,3,1,2] -> [5,3,1,2,14]
        printArr(obj.resultArray(new int[]{5,14,3,1,2}));
        // [2,1,3,3] -> [2,3,1,3]
        printArr(obj.resultArray(new int[]{2,1,3,3}));
        // [3,3,3,3] ->  [3,3,3,3]
        printArr(obj.resultArray(new int[]{3,3,3,3}));

    }

    public static void printArr(int[] arr) {
        for(int num: arr) {
            System.out.print(num + ", ");
        }
        System.out.println("");
    }
}
