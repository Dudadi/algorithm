package segment;

public class SumSegmentTree {
    int[] arr;
    int n;
    public SumSegmentTree(int[] arr) {
        this.n = arr.length;
        this.arr = new int[n*2];
        for(int i=0;i<this.n;i++) {
            this.arr[i+this.n] = arr[i];
        }
        for(int i=this.n-1;i>0;i--) {
            this.arr[i] = this.arr[i*2] + this.arr[i*2+1];
        }
    }
    public void update(int i, int val) {
        i+=this.n;
        this.arr[i] = val;
        while(i>1) {
            i>>=1;
            this.arr[i] = this.arr[i*2]+this.arr[i*2+1];
        }
    }

    public int getSum(int l, int r) {
        l+=n;
        r+=n;
        int sum = 0;
        while(l<=r) {
            if((l&1) == 1) {
                sum += this.arr[l];
                l++;
            }
            if((r&1) == 0) {
                sum += this.arr[r];
                r--;
            }
            l>>=1;
            r>>=1;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("hello SumSegmentTree");

        SumSegmentTree tree = new SumSegmentTree(new int[]{1,2,3,4,5});
        System.out.println(tree.getSum(0,2)); // 6
        System.out.println(tree.getSum(0,4)); // 15
        System.out.println(tree.getSum(2,4)); // 12
        tree.update(2, 10);
        System.out.println(tree.getSum(2, 4)); //19

    }
}
