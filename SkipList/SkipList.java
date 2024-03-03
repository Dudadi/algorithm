/**
 * skiplist 出自Skip lists: a probabilistic alternative to balanced trees
 * 本身是平衡树的一种备选方案，平衡树如红黑树可以实现OlogN时间复杂度的查询效率，
 * 而调表skiplist查询的时间复杂度是O(n)。可以实现Set, sortedSet,map,sortedMap等数据结构。
 * 其原理是通过跳跃节点进行查询，加快查询速度。目前使用的项目有 redis,bigtable。
 * */
package SkipList;

import java.util.ArrayList;
import java.util.List;

public class SkipList<K,V> {
    public int MAX_LEVEL = 10;
    public int curLevel;
    public int nodeCount;
    Node<K,V>head;
    Node<K,V>tail;

    public SkipList() {
        curLevel = 0;
        nodeCount = 0;
        head = new Node<>();
        head.next = new ArrayList<>();
        tail = new Node<>();
        tail.next = new ArrayList<>();
        for(int i=0;i<MAX_LEVEL;i++) {
            head.next.add(null);
            tail.next.add(null);
        }
    }

    class Node<K,V> {
        public K key;
        public V value;
        List<Node> next;
        Node() {}
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 查找的过程往右下方向查找，如果右边有节点，就往右查找，如果没有就往下查找。
     * 其中往右查找的过程会跳跃中间高度小于当前高度的节点。
     * */
    public V search(K key) {
        Node<K, V>node = head;
        for (int i = curLevel; i >= 0; --i) {
            while(node.next.get(i) != null && ((Comparable)key).compareTo(node.next.get(i).key) > 0  ) {
                node = node.next.get(i);
            }
        }
        node = node.next.get(0);
        if(node != null && ((Comparable)key).compareTo(node.key) == 0) {
            return node.value;
        }
        return null;
    }

    /**
     * 插入的过程是，先进行查找，获取当前值对应的节点位置（跳表是有序的）
     * 然后再随机设置当前节点的高度，并插入不同高度从左到右的节点。
     * */
    boolean insert(K key, V value) {
        Node[] update = new Node[MAX_LEVEL];
        Node<K,V>node = head;
        for (int i = curLevel; i >= 0; --i) {
            while(node.next.get(i) != null && ((Comparable)key).compareTo(node.next.get(i).key) > 0  ) {
                node = node.next.get(i);
            }
            update[i] = node;
        }
        node = node.next.get(0);
        if(node != null && ((Comparable)key).compareTo(node.key) == 0) {
            return false;
        }
        int nodeLevel = (int)(Math.random() * MAX_LEVEL);
        if(nodeLevel > curLevel) {
            curLevel++;
            nodeLevel = curLevel;
            update[nodeLevel] = head;
        }
        Node<K,V>newNode = createNode(nodeLevel, key, value);
        for(int i=nodeLevel;i>=0;i--) {
            Node updateNode = update[i];
            newNode.next.set(i, (Node) updateNode.next.get(i));
            updateNode.next.set(i, newNode);
        }
        return true;
    }

    /**
     * 删除的过程是，先进行查找，获取当前值对应的节点位置（跳表是有序的）
     * 删除不同高度从左到右的节点。
     * */
    boolean remove(K key) {
        Node[] update = new Node[MAX_LEVEL];
        Node<K,V>node = head;
        for (int i = curLevel; i >= 0; --i) {
            while(node.next.get(i) != null && ((Comparable)key).compareTo(node.next.get(i).key) > 0  ) {
                node = node.next.get(i);
            }
            update[i] = node;
        }
        node = node.next.get(0);
        if(((Comparable)key).compareTo(node.key) != 0) {
            return false;
        }

        for(int i=0;i<= curLevel;i++) {
            if(update[i].next.get(i) != node) {
                break;
            }
            update[i].next.set(i,node.next.get(i));
        }
        return true;
    }

    Node<K,V> createNode(int nodeLevel, K key, V value) {
        Node<K,V>node = new Node<K, V>();
        node.key = key;
        node.value = value;
        node.next = new ArrayList<>(nodeLevel+1);
        for(int i=0;i<=nodeLevel;i++) {
            node.next.add(null);
        }
        return node;
    }

    public static void main(String[] args) {
        Integer i = 1, j = 2;
        System.out.println(i.compareTo(j));
        SkipList<Integer,String>skiplist = new SkipList<Integer, String>();
        System.out.println(skiplist.insert(1,"1"));
        System.out.println(skiplist.insert(2,"2"));
        System.out.println(skiplist.insert(3,"3"));
        System.out.println(skiplist.search(3));
        System.out.println(skiplist.remove(3));
        System.out.println(skiplist.search(3));
        System.out.println(skiplist.search(4));
        System.out.println(skiplist.search(0));

    }
}
