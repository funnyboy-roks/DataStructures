package com.funnyboyroks.DataStructures.Maps;

import java.util.*;
import java.util.stream.Collectors;

public class TreeMap<K extends Comparable<K>, V> implements Map<K, V> {

    public static void main(String[] args) {
        Map<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.put("d", 4);
        map.put("e", 5);
        System.out.println(map.get("a"));
        System.out.println(map + " " + map.size());
        map.remove("b");
        System.out.println(map + " " + map.size());
    }

    private int  size;
    private Node head;

    public TreeMap() {
        this.size = 0;
        this.head = null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    public boolean containsKey(K key) {
        Node n = this.head;
        while (n != null) {
            int compare = key.compareTo(n.entry.key);
            if (compare == 0) return true;
            if (compare > 0) {
                n = n.right;
            } else {
                n = n.left;
            }
        }
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.containsKey((K) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.values().stream().anyMatch(value::equals);
    }

    public V get(K key) {
        Node n = this.head;
        while (n != null) {
            int compare = key.compareTo(n.entry.key);
            if (compare == 0) return n.entry.value;
            if (compare > 0) {
                n = n.right;
            } else {
                n = n.left;
            }
        }
        return null;
    }

    @Override
    public V get(Object key) {
        return this.get((K) key);
    }

    @Override
    public V put(K key, V value) {
        if (this.head == null) {
            this.head = new Node(new Entry(key, value));
            this.size++;
            return null;
        }
        Node n = this.head;
        while (true) {
            int compare = key.compareTo(n.entry.key);
            if (compare == 0) {
                V oldValue = n.entry.value;
                n.entry.value = value;
                return oldValue;
            }
            if (compare > 0) {
                if (n.right == null) {
                    n.right = new Node(new Entry(key, value));
                    this.size++;
                    return null;
                }
                n = n.right;
            } else {
                if (n.left == null) {
                    n.left = new Node(new Entry(key, value));
                    this.size++;
                    return null;
                }
                n = n.left;
            }
        }
    }

    @Override
    public V remove(Object key) {
        return this.remove((K) key);
    }

    public V remove(K key) {
        V prev = this.get(key);
        if(prev == null) return null;
        Map<K, V> tmp = this.entrySet().stream().filter(e -> e.getKey().compareTo(key) != 0).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.clear();
        this.putAll(tmp);
        return prev;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public Set<K> keySet() {
        return this.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Collection<V> values() {
        return this.entrySet().stream().map(Map.Entry::getValue).toList();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.head != null ? this.head.inOrder() : new HashSet<>();
    }

    @Override
    public String toString() {
        return this.entrySet().stream().map(Object::toString).collect(Collectors.joining(", ", "{", "}"));
    }

    private class Entry implements Map.Entry<K, V> {

        private final K key;
        private       V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V prev = this.value;
            this.value = value;
            return prev;
        }

        @Override
        public String toString() {
            return String.format("%s=%s", this.key, this.value);
        }
    }

    private class Node {

        private final Entry entry;
        private       Node  left;
        private       Node  right;

        public Node(Entry entry) {
            this.entry = entry;
            this.left = null;
            this.right = null;
        }

        private Set<Map.Entry<K, V>> inOrder() {
            Set<Map.Entry<K, V>> set = new HashSet<>();
            if (this.left != null) {
                set.addAll(this.left.inOrder());
            }
            set.add(this.entry);
            if (this.right != null) {
                set.addAll(this.right.inOrder());
            }
            return set;
        }

    }



}
