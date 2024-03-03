/**
 * LRU least recently used 最近最少使用，是一种常见的页面置换算法。
 * 以下的LRU，可以实现O(1)时间复杂度的插入，删除和查询功能。
 * 通过map去查询节点，并在节点中缓存该节点pre和next节点，进行插入和删除
 *
 * */
import java.util.HashMap;
import java.util.Map;

public class LRU<K, V> {
    Node<K,V> head;
    Node<K,V> tail;
    int size;
    int capacity;
    Map<K, Node<K,V>> map;
    class Node<K,V> {
        Node pre;
        Node next;
        K key;
        V value;
        Node() {}
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
        Node(K key, V value, Node pre, Node next) {
            this.key = key;
            this.value = value;
            this.pre = pre;
            this.next = next;
        }
    }
    public LRU(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        map = new HashMap<>();
        head = new Node<K,V>();
        tail = new Node<K,V>();
        head.next = tail;
        tail.pre = head;
    }
    public V get(K key) {
        Node<K,V> node = map.getOrDefault(key, null);
        if(node != null) {
            removeNode(node);
            insertToTail(node);
        }
        return node==null ? null : node.value;
    }
    public void put(K key, V value) {
        remove(key);
        Node<K,V> node = new Node(key,value);
        insertToTail(node);
        if(size>capacity) {
            removeNode(head.next);
        }
    }
    public int remove(K key) {
        Node node = map.getOrDefault(key, null);
        if(node == null) {
            return -1;
        }
        removeNode(node);
        return 0;
    }

    private void insertToTail(Node<K,V> node) {
        tail.pre.next = node;
        node.pre = tail.pre;
        node.next = tail;
        tail.pre = node;
        map.put(node.key, node);
        size++;
    }

    private void removeNode(Node<K,V> node) {
        map.remove(node.key);
        node.pre.next = node.next;
        node.next.pre = node.pre;
        size--;
    }
    public void printLRU() {
        Node node = head;
        System.out.println("print current lru: ");
        while(node.next != tail) {
            System.out.print(node.next.value.toString() + " ");
            node = node.next;
        }
        System.out.println();
    }
    public static void main(String[] args) {
        System.out.println("Hello world!");
        LRU<Integer, String> lru = new LRU<>(3);
        lru.put(1,"hello");
        lru.printLRU();
        lru.put(2, "world");
        lru.printLRU();
        lru.put(3, "lru");
        lru.printLRU();
        lru.put(4, "java");
        lru.printLRU();
        lru.get(1);
        lru.printLRU();
        lru.get(4);
        lru.printLRU();
    }
}
