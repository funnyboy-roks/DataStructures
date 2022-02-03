package com.funnyboyroks.DataStructures.Hashing;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class HashSetLinearProbing<E> implements Set<E> {

    private int   size;
    private E[]   data;
    private int[] status;

    public HashSetLinearProbing() {

        this.size = 0;
        this.data = (E[]) new Object[13];
        this.status = new int[this.data.length];
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
        if (o == null) throw new IllegalArgumentException("Object can not be null.");
        int hash = this.hash(o);
        if (this.status[hash] <= 0) { // Item not there
            return false;
        }
        int i = hash;
        do {
            if (Objects.equals(o, this.data[i])) return true;
            if (this.status[i] <= 0) return false;
            ++i;
            i %= this.data.length;
        } while (i != hash);
        return false;
    }

    @Override
    public boolean add(E e) {
        if (e == null) throw new IllegalArgumentException("Object can not be null.");
        int hash = this.hash(e);
        if (this.status[hash] <= 0) {
            this.data[hash] = e;
            this.status[hash] = 1;
        }
        int i = hash;
        do {

//            if(this.status[hash] <= )

            ++i;
            i %= this.data.length;
        } while(i != hash);
        return false;
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
        this.size = 0;
        this.data = (E[]) new Object[this.data.length];
        this.status = new int[this.data.length];
    }

    private int hash(Object o) {
        return Math.abs(o.hashCode()) % this.data.length;
    }
}
