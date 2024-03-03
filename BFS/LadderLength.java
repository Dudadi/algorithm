/**
 * 该题是leetcode127题：单词接龙。给定beginWord、endWord和字典wordList，
 * 找到从beginWord到endWord转化的最短序列的长度。每一次转化只能转化单词中一个字母，
 * 且转化序列中的单词需要在字典中。
 * 解法1： dfs
 *      从beginWord开始遍历，依次从字典中获取下一次可行的单词，为了防止循环序列，
 *      需要增加一个flag记录已经遍历的单词
 *
 * 解法2： bfs
 *      也是从beginWord开始遍历，只不过通过queue缓存下一步可行解。一般获取最短路径之类的用bfs由于dfs,
 *      dfs要遍历所有可行解，而bfs如果找到一个解，那这个解肯定是最优解
 * 解法3： bfs优化
 *      优化点：
 *      1. 字典长度一般很长，通过修改当前遍历单词的各个位数的字母的次数却仅为26 * word.length();
 *      2. 缓存beginWords和endWords两个set,仅遍历长度短一点的set
 *
 * */

package BFS;

import java.util.*;

class LadderLength {
    public  int res;
    public  List<String> list;
    public  boolean[] flag;

    public  int ladderLength_bfs(String beginWord, String endWord, List<String> wordList) {
        if(!wordList.contains(endWord)) {
            return 0;
        }
        // 遍历的长度；
        int length = 0;
        Queue<String> queue = new LinkedList<>();
        Map<String,Boolean> map = new HashMap<>();
        queue.add(beginWord);

        while(!queue.isEmpty()) {
            int size = queue.size();
            length++;
            while(size-->0) {
                String top = queue.poll();

                if(top == endWord) {
                    return length;
                }
                if(map.getOrDefault(top,false)) {
                    continue;
                }
                for(String str : wordList) {
                    if(!map.getOrDefault(top,false) && isNear(str, top)) {
                        queue.add(str);
                    }
                }
                map.put(top,true);
            }
        }
        return 0;
    }

    public  int ladderLength_dfs(String beginWord, String endWord, List<String> wordList) {
        res = wordList.size() + 1;
        list = wordList;
        flag = new boolean[list.size()];
        dfs(beginWord,endWord,1);
        return res == wordList.size()  + 1 ? 0 : res;
    }

    public  void dfs(String currentWord, String endWord, int length) {
        if(currentWord == endWord) {
            res = Math.min(res, length);
            return;
        }
        for(int i=0;i<list.size();i++) {
            if(!flag[i] && isNear(currentWord,list.get(i))) {
                flag[i] = true;
                dfs(list.get(i), endWord, length+1);
                flag[i] = false;
            }
        }
    }
    public  int ladderLength_bfs_ref(String beginWord, String endWord, List<String> wordList) {
        Set<String>words = new HashSet<>(wordList);
        if(!words.contains(endWord)) return 0;

        Set<String>beginWords = new HashSet<>();
        Set<String>endWords = new HashSet<>();
        beginWords.add(beginWord);
        endWords.add(endWord);
        Set<String>visitedWords = new HashSet<>();
        visitedWords.add(beginWord);

        int length = 1;
        while(!beginWords.isEmpty()) {
            if(beginWords.size() > endWords.size()) {
                Set<String> tmp = beginWords;
                beginWords = endWords;
                endWords = tmp;
            }
            Set<String>newBeginWords = new HashSet<>();
            for(String word: beginWords) {
                char[] chars = word.toCharArray();
                for(int i=0;i<chars.length;i++) {
                    char ch = chars[i];
                    for(int j=0;j<26;j++) {
                        chars[i] = (char)('a' + j);
                        String newWord = new String(chars);
                        if(endWords.contains(newWord)) {
                            return length+1;
                        }
                        if(words.contains(newWord) && !visitedWords.contains(newWord)) {
                            newBeginWords.add(newWord);
                            visitedWords.add(newWord);
                        }
                    }
                    chars[i] = ch;
                }
            }
            beginWords = newBeginWords;
            length++;
        }
        return 0;
    }

    public  boolean isNear(String str1, String str2) {
        int length = str1.length();
        int differ = 0;
        for(int i=0;i<length;i++) {
            if(str1.charAt(i) != str2.charAt(i)) {
                differ++;
                if(differ >1){
                    return false;
                }
            }
        }
        return differ == 1;
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        String[][] words = {{"hit","cog"},{"hit","cog"},{"hit","log"}};
        List<String> list1 = Arrays.asList("hot","dot","dog","lot","log","cog");
        List<String> list2 = Arrays.asList("hot","dot","dog","lot","log");
        List<List<String>> lists = new ArrayList<>();
        lists.add(list1);
        lists.add(list2);
        lists.add(list1);
        int[] res = {5,0,4};
        for(int i=0;i<words.length;i++) {
            System.out.println("Test : "+ i);
            System.out.println(words[i][0] + " To "+ words[i][1]);
            int ret = new LadderLength().ladderLength_bfs_ref(words[i][0],words[i][1],lists.get(i));
            System.out.println(" 结果： "+ ret + " 正确值： "+ res[i] + "是否相等： "+ (ret == res[i]));
        }
    }



}
