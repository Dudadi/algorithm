/**
 * 拓扑排序算法，类似leetcode 269,444,210，该题为210
 * */
package TopologySort;

import java.util.*;

public class FindOrder {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        int[][][] prerequisites = {{{1,0}},{{1,0},{2,0},{3,1},{3,2}}};
        int[] courseNums = {2,4};
        int[][] res = {{0,1},{0,1,2,3}};

        for(int i=0;i<prerequisites.length;i++) {
            System.out.println("\nTest: " + i);
            System.out.println("数组: ");
            for(int j=0;j<prerequisites[i].length;j++) {
                printArray(prerequisites[i][j]);
            }
            int[] ret = findOrder_bfs(courseNums[i], prerequisites[i]);
            System.out.println("结果：");
            printArray(ret);
            System.out.println("正确值：");
            printArray(res[i]);
            System.out.println("是否相等： "+ (Arrays.equals(ret,res[i])));
        }

    }
    static Stack<Integer>stack ;
    static Map<Integer, List<Integer>> map;
    static int[] flag ;
    static int[] res;
    static boolean isValid;
    public static int[] findOrder_dfs(int numCourses, int[][] prerequisites) {
        stack = new Stack<>();
        map = new HashMap<>();
        res = new int[numCourses];
        flag = new int[numCourses];
        isValid = true;
        for(int i=0;i<numCourses;i++) {
            map.put(i,new ArrayList<>());
        }
        for(int i=0;i<prerequisites.length;i++) {
            map.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }
        for(int i=0;i<numCourses;i++) {
            if(flag [i] == 0) {
                dfs(i);
            }
        }
        if(!isValid) {
            return new int[]{};
        }
        int index=0;
        while(!stack.isEmpty()) {
            res[index++] = stack.pop();
        }
        return res;
    }
    public static void dfs(int index) {
        flag[index] = 1;
        List<Integer> list = map.get(index);

        for(int i: list) {
            if(flag[i] == 1) {
                isValid = false;
                return;
            } else if(flag[i] == 0) {
                dfs(i);
                if(!isValid){
                    return;
                }
            }
        }
        flag[index] =2;
        stack.push(index);
    }
    public static int[] findOrder_bfs(int numCourses, int[][] prerequisites) {
        List<Integer> res = new ArrayList<>();
        Map<Integer,List<Integer>> map = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        int[] indegree = new int[numCourses];

        for(int i=0;i<numCourses;i++) {
            map.put(i,new ArrayList<>());
        }

        for(int i=0;i<prerequisites.length;i++) {
            map.get(prerequisites[i][1]).add(prerequisites[i][0]);
            indegree[prerequisites[i][0]]++;
        }
        for(int i=0;i<numCourses;i++) {
            if(indegree[i] == 0){
                queue.offer(i);
            }
        }
        while(!queue.isEmpty()) {
            int top = queue.poll();
            res.add(top);

            for(int index : map.get(top)) {
                indegree[index]--;
                if(indegree[index] == 0){
                    queue.offer(index);
                }
            }
        }
        if(res.size() != numCourses){
            return new int[]{};
        }

        return res.stream().mapToInt(i -> i).toArray();
    }
    public static void printArray(int[] arr) {
        for(int i=0;i<arr.length;i++) {
            System.out.print(arr[i]+ " ");
        }
        System.out.println();
    }
}
