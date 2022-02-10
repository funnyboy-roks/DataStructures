package com.funnyboyroks.DataStructures.Hashing;

import java.lang.reflect.Array;
import java.util.*;

public class HashSetChaining<E> implements Set<E> {

    public static void main(String[] args) {
        var set = new HashSetChaining<Integer>();
        for (int i = 0; i < 10; ++i) {
            int num = (int) (Math.random() * 100);
            boolean out = set.add(num);
            System.out.printf("%d - %s - %d%n", num, out, set.buckets.length);
        }

        System.out.println(Arrays.toString(set.toArray(Integer[]::new)));
        System.out.println(set);
        System.out.println(set.size() + " - " + set.buckets.length);
    }

    private static final double LOAD_FACTOR_THRESHOLD = .75;

    private int    size;
    private Node[] buckets;

    public HashSetChaining() {
        this.size = 0;
        this.buckets = (Node[]) Array.newInstance(Node.class, 13);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        int index = hash(o);
        Node current = this.buckets[index];
        while (current != null) {
            if (current.data.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return Arrays
            .stream(this.toArray())
            .map(n -> (E) n)
            .iterator();
    }

    @Override
    public Object[] toArray() {
        if (this.size == 0) return new Object[0];
        Object[] vals = new Object[this.size];
        int i = 0;
        for (Node bucket : this.buckets) {
            if (bucket == null) continue;
            Node current = bucket;
            while (current != null) {
                vals[i++] = current.data;
                current = current.next;
            }
        }
        return vals;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) this.toArray();
    }

    @Override
    public boolean add(E e) {
        int hash = hash(e);
        Node current = this.buckets[hash];
        while (current != null) {
            if (current.data.equals(e)) {
                return false;
            }
            current = current.next;
        }
        this.buckets[hash] = new Node(e, this.buckets[hash]);
        ++this.size;

        if (this.size / (double) this.buckets.length > LOAD_FACTOR_THRESHOLD) {
            this.rehash();
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int hash = hash(o);
        Node current = this.buckets[hash];
        Node previous = null;
        while (current != null) {
            if (current.data.equals(o)) {
                if (previous == null) {
                    this.buckets[hash] = current.next;
                } else {
                    previous.next = current.next;
                }
                --this.size;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return c.stream().map(this::add).toList().stream().anyMatch(x -> x);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        for (int i = 0; i < this.buckets.length; i++) {
            Node bucket = this.buckets[i];
            if (bucket == null) continue;
            Node current = bucket;
            Node prev = null;
            while (current != null) {
                if (!c.contains(current.data)) {
                    if (prev == null) {
                        this.buckets[i] = current.next;
                    } else {
                        prev.next = current.next;
                    }
                    changed = true;
                }
                prev = current;
                current = current.next;
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return c.stream().map(this::remove).toList().stream().anyMatch(x -> x);
    }

    @Override
    public void clear() {
        this.resize(13);
    }

    private void resize(int size) {
        this.size = 0;
        this.buckets = (Node[]) Array.newInstance(Node.class, size);
    }

    private int hash(Object o) {
        return Math.abs(o.hashCode()) % this.buckets.length;
    }

    private void rehash() {
        Node[] oldBuckets = this.buckets;
        this.resize(2 * oldBuckets.length);
        for (Node bucket : oldBuckets) {
            if (bucket == null) continue;
            Node current = bucket;
            while (current != null) {
                this.add(current.data);
                current = current.next;
            }
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(this.toArray());
    }

    private class Node {

        private final E    data;
        private       Node next;

        public Node(E data) {
            this.data = data;
            this.next = null;
        }

        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return this.data.toString();
        }
    }
}
