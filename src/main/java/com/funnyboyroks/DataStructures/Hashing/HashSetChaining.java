package com.funnyboyroks.DataStructures.Hashing;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class HashSetChaining<E> implements Set<E> {

    private int size;
    private Node[] data;

    public HashSetChaining() {

        this.size = 0;
        this.data = (Node[]) new Object[13];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new RuntimeException("NYI");
    }

    @Override
    public Iterator<E> iterator() {
        throw new RuntimeException("NYI");
    }

    @Override
    public Object[] toArray() {
        throw new RuntimeException("NYI");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new RuntimeException("NYI");
    }

    @Override
    public boolean add(E e) {
        throw new RuntimeException("NYI");
    }

    @Override
    public boolean remove(Object o) {
        throw new RuntimeException("NYI");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException("NYI");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new RuntimeException("NYI");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException("NYI");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException("NYI");
    }

    @Override
    public void clear() {
        throw new RuntimeException("NYI");
    }

    private int hash(Object o) {
        return Math.abs(o.hashCode()) % data.length;
    }

    private class Node {
        private final E data;
        private Node child;

        public Node(E data) {
            this.data = data;
        }
    }
}
