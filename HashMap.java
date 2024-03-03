/**
 * 实现简易版本的hashmap，通过链式处理hash冲突；
 * 后续优化：
 * 1. 自动处理扩容缩容；
 * 2. 如果链式超长通过红黑树处理；
 * 3. 增加遍历函数
 * */
public class HashMap<K,V>{
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    int capacity;
    float factor;
    Node[] table;
    int size;
    public HashMap(int capacity) {
        this.factor = DEFAULT_LOAD_FACTOR;
        this.capacity = (int)(capacity/this.factor);
        this.table = new Node[capacity];
        this.size = 0;
    }

    public HashMap(int capacity, float factor) {
        this.factor = factor;
        this.capacity = (int)(capacity/this.factor);
        this.table = new Node[capacity];
        this.size = 0;
    }

    public class Node<K,V> {
        K key;
        V value;
        Node next;
    }

    public V get(K k) {
        int hash = k.hashCode() % this.capacity;
        Node<K,V> node = table[hash];
        while(node != null) {
            if(node.key.equals(k)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }
    public void remove(K k) {
        int hash = k.hashCode() % this.capacity;
        Node head = table[hash], node = head;
        if(node == null) return;
        if(node.key.equals(k)) {
            size--;
            table[hash] = node.next;
        }
        while(node.next != null) {
            if(node.key.equals(k)) {
                size--;
                node.next = node.next.next;
            }
            node = node.next;
        }
    }

    public void put(K k, V v) {
        remove(k);
        int hash = k.hashCode() % this.capacity;
        Node head = table[hash];
        Node node = new Node();
        node.key = k;
        node.value = v;
        node.next = head;
        table[hash] = node;
        size++;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args){
        HashMap<Integer,String> map = new HashMap<Integer,String> (10);
        map.get(1);
        map.remove(1);
        map.put(1,"2");
        System.out.println( map.get(1));
        System.out.println(map.size());
        map.put(1,"3");
        System.out.println(map.size());
        map.put(2,"4");
        System.out.println(map.size());
    }
}
