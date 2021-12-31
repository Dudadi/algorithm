package Trie;

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
public class Trie {
    class TrieNode {
        boolean isFinished;
        char ch;
        public TrieNode[] next = new TrieNode[26];

        public TrieNode() {}

        public TrieNode(char ch) {
            this.ch = ch;
        }
    }
    public TrieNode root;

    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode(' ');
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode tmp = root;
        char[] chars = word.toCharArray();
        for(int i=0;i<chars.length;i++) {
            char ch = chars[i];
            TrieNode node = tmp.next[ch-'a'];
            if(node == null) {
                tmp.next[ch-'a'] = new TrieNode(ch);

            }
            tmp = tmp.next[ch-'a'];
        }
        tmp.isFinished = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode tmp = root;
        char[] chars = word.toCharArray();
        for(int i=0;i<chars.length;i++) {
            char ch = chars[i];
            TrieNode node = tmp.next[ch-'a'];
            if(node == null) {
                return false;

            }
            tmp = tmp.next[ch-'a'];
        }
        return tmp.isFinished;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode tmp = root;
        char[] chars = prefix.toCharArray();
        for(int i=0;i<chars.length;i++) {
            char ch = chars[i];
            TrieNode node = tmp.next[ch-'a'];
            if(node == null) {
                return false;

            }
            tmp = tmp.next[ch-'a'];
        }
        return true;
    }
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Trie trie = new Trie();

        trie.insert("apple");
        System.out.println(trie.search("apple") == true);   // returns true
        System.out.println(trie.search("app") == false);     // returns false
        System.out.println( trie.startsWith("app") == true); // returns true
        trie.insert("app");
        System.out.println(trie.search("app") == true);     // returns true
    }
}
