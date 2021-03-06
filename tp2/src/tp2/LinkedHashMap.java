package tp2;

import java.util.Arrays;

public class LinkedHashMap<KeyType, DataType> {

    private static final double COMPRESSION_FACTOR = 2; // 50%
    private static final int DEFAULT_CAPACITY = 20;
    private static final int CAPACITY_INCREASE_FACTOR = 2;

    private Node<KeyType, DataType>[] map;
    private int capacity;
    private int size = 0;

    public LinkedHashMap() {
        capacity = DEFAULT_CAPACITY;
        map = new Node[DEFAULT_CAPACITY];
    }

    public LinkedHashMap(int capacity) {
        this.capacity = capacity;
        map = new Node[capacity];
    }

    /**
     * Finds the index attached to a particular key
     * @param key Value used to access to a particular instance of a DataType within map
     * @return The index value attached to a particular key
     */
    private int getIndex(KeyType key){
        int keyHash = key.hashCode() % capacity;
        return keyHash < 0 ? -keyHash : keyHash;
    }

    private boolean shouldRehash() {
        return size * COMPRESSION_FACTOR > capacity;
    }

    /**
     * Increases capacity by CAPACITY_INCREASE_FACTOR (multiplication) and
     * reassigns all contained values within the new map
     */
    private void rehash() {
        // On double la capacité
        capacity *= CAPACITY_INCREASE_FACTOR;

        // On créé une nouvelle map vide avec la nouvelle capacité
        Node<KeyType, DataType>[] map2 = new Node[capacity];
        for (Node<KeyType, DataType> mapCell : map) {

            if (mapCell == null)
                continue;

            Node node = mapCell;
            do {
                // Trouve le nouvel index pour la nouvelle capacité
                int newIdx = getIndex((KeyType) node.key);

                if (map2[newIdx] == null)
                    map2[newIdx] = node;
                else {
                    Node neighborNode = map2[newIdx];

                    while (neighborNode.next != null)
                        neighborNode = neighborNode.next;

                    neighborNode.next = node;
                }
                node = node.next;

            } while (node != null);

        }

        map = map2;
    }

    public int size() {
        return size;
    }

    public int getCapacity(){
        return capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Finds if map contains a key
     * @param key Key which we want to know if exists within map
     * @return if key is already used in map
     */
    public boolean containsKey(KeyType key) {
        // 1) Use getIndex to determine which list to traverse
        int idx = getIndex(key);

        // 2) Verify if there's actually a list
        if (map[idx] != null){
            Node node = map[idx];

            do {
                if (node.key.equals(key))
                        return true;
                node = node.next;
            } while (node != null);

            return false;
        }
        // No list at corresponding index thus no item with that key
        return false;
    }

    /**
     * Finds the value attached to a key
     * @param key Key which we want to have its value
     * @return DataType instance attached to key (null if not found)
     */
    public DataType get(KeyType key) {

        if (!containsKey(key)) {
            return null;
        }

        Node node = map[getIndex(key)];
        DataType data = null;
        do {
            if (node.key.equals(key)){
                data = (DataType) node.data;
                break;
            }
            node = node.next;
        } while (node != null);

        return data;
    }

    /**
     * Assigns a value to a key
     * @param key Key which will have its value assigned or reassigned
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType put(KeyType key, DataType value) {

        if (shouldRehash())
            rehash();

        int idx = getIndex(key);

        if (map[idx] == null){
            map[idx] = new Node<>(key,value);
        } else {
            Node<KeyType,DataType> node = map[idx];
            do {
                if (node.key.equals(key)){
                    DataType oldData = node.data;
                    node.data = value;
                    return oldData;
                }
                node = node.next;
            } while (node != null);
            Node<KeyType,DataType> newNode = new Node<>(key,value);
            newNode.next = map[idx];
            map[idx] = newNode;
        }
        size++;
        return null;
    }

    /**
     * Removes the node attached to a key
     * @param key Key which is contained in the node to remove
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType remove(KeyType key) {

        if (!containsKey(key))
            return null;

        int idx = getIndex(key);
        Node<KeyType,DataType> node = map[idx];
        Node<KeyType,DataType> nextNode = node.next;
        DataType oldData = null;
        if (node.key.equals(key)){
            oldData = node.data;
            map[idx] = nextNode;
        } else {
            while (nextNode != null){

                if (nextNode.key.equals(key)){
                    oldData = nextNode.data;
                    node.next = nextNode.next;
                    break;
                }
                node = nextNode;
                nextNode = node.next;
            }
        }
        size--;
        return oldData;
    }

    /**
     * Removes all nodes contained within the map
     */
    public void clear() {
        // Test asks for a map of same initial size and filled
        // with null objects
        Arrays.fill(map,null);
    }


    static class Node<KeyType, DataType> {
        final KeyType key;
        DataType data;
        Node next; // Pointer to the next node within a Linked List

        Node(KeyType key, DataType data)
        {
            this.key = key;
            this.data = data;
            next = null;
        }
    }
}


