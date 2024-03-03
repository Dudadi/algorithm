/**
 * UnoinFind处理连通问题。
 * 下面leetcode 1135,一般如果不仅仅是判断是否连通，还需要最少步数连通，需要增加优先队列
 * */
package UnionFind;

import java.util.PriorityQueue;

public class MinimumCost {
    static int[] pre;
    public static int MinimumCost(int N,int[][] connections) {
        int cost = 0, m = connections.length;
        pre= new int[N+1];
        for(int i=0;i<N+1;i++) {
            pre[i] = i;
        }
        PriorityQueue<int[]>priorityQueue = new PriorityQueue<>((int[] connection1, int[] connection2) -> {
            return connection1[2] - connection2[2];
        });
        for(int i=0;i<m;i++) {
            priorityQueue.offer(connections[i]);
        }
        while(!priorityQueue.isEmpty()) {
            int[] connection = priorityQueue.poll();
            if(isUion(connection)) {
                cost += connection[2];
            }
        }
        for(int i=1;i<N+1;i++) {
            if(unionSearch(i) != 1){
                return -1;
            }
        }
        return cost;

    }
    static boolean isUion(int[] connection) {
        int city1 = connection[0];
        int city2 = connection[1];
        int root1 = unionSearch(city1);
        int root2 = unionSearch(city2);
        if(root1 == root2)return false;
        int min = Math.min(root1, root2);
        pre[city1] = min;
        pre[city2] = min;
        return true;
    }
    static int unionSearch(int root) {
        int son = root;
        while(pre[root]!=root) {
            root = pre[root];
        }
        while(son != root) {
            int tmp = pre[son];
            pre[son] = root;
            son = tmp;
        }
        return root;
    }
    public static void main(String[] args) {
        System.out.println("Hello world!");

        int[][][] paths = {{{1,2,5}, {1,3,6},{2,3,1}},{{1,2,2},{1,3,1},{2,3,3},{2,4,4},{3,4,5}}};
        int[] res = {6,7};
        int[] nums = {3,4};
        for(int i=0;i<res.length;i++) {
            System.out.println("Test "+ i);
            System.out.println("路径 "+ i + ": ");
            for(int j=0;j<paths[i].length;j++) {
                for(int k=0;k<paths[i][j].length;k++) {
                    System.out.print(paths[i][j][k] + ", ");
                }
                System.out.println(" ");
            }
            System.out.println("结果： "+ MinimumCost(nums[i],paths[i])+"  是否正确： "+ (MinimumCost(nums[i],paths[i]) == res[i]));
            System.out.println(" ");
        }
    }
}
