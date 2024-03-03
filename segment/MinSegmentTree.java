package segment;

public class MinSegmentTree {
    int[] arr;
    int n;
    public MinSegmentTree(int[] arr) {
        this.n = arr.length;
        this.arr = new int[2*n];
        for(int i=n;i<2*n;i++) {
            this.arr[i] = arr[i-n];
        }
        for(int i=n-1;i>0;i--) {
            this.arr[i] = Math.min(this.arr[i*2], this.arr[i*2+1]);
        }
    }
    public void update(int i, int val) {
        i += n;
        arr[i] = val;
        while(i > 1) {
            i = i/2;
            arr[i] = Math.min(arr[i*2], arr[i*2+1]);
        }
    }
    // get min of region [l, r]
    public int getMin(int l, int r) {
        l += n;
        r += n;
        int min = Integer.MAX_VALUE;
        while(l <= r) {
            if((l&1) == 1) {
                min = Math.min(min, arr[l]);
                l++;
            }
            if((r&1) == 0) {
                min = Math.min(min, arr[r]);
                r--;
            }
            l >>= 1;
            r >>= 1;
        }
        return min;
    }
    public static void main(String[] args) {
        System.out.println("hello MinSegmentTree");
        MinSegmentTree tree = new MinSegmentTree(new int[]{7,5,3,9,8,1,10});
        System.out.println(tree.getMin(0,0)); // 7
        System.out.println(tree.getMin(0,6)); // 1
        System.out.println(tree.getMin(1,4)); // 3
        System.out.println(tree.getMin(1,2)); // 3
        System.out.println(tree.getMin(3,4)); // 8
        tree.update(5,12);
        System.out.println(tree.getMin(3,6)); // 8
    }
}
