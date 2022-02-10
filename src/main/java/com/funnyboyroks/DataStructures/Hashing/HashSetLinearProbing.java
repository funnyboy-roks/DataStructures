package com.funnyboyroks.DataStructures.Hashing;

import java.lang.reflect.Array;
import java.util.*;

public class HashSetLinearProbing<E> implements Set<E> {

    public static void main(String[] args) {
        var set = new HashSetLinearProbing<Integer>();
        for (int i = 0; i < 10; ++i) {
            int num = (int) (Math.random() * 100);
            boolean out = set.add(num);
            System.out.printf("%d - %s - %d%n", num, out, set.buckets.length);
        }

        System.out.println(Arrays.toString(set.toArray(Integer[]::new)));
        System.out.println(set.size() + " - " + set.buckets.length);
    }

    private static final double LOAD_FACTOR_THRESHOLD = .75;

    private int       size;
    private Type<E>[] buckets;
    private int[]     bucketStatus; // Uses the statuses below

    private static final int DELETED = -1;
    private static final int EMPTY   = 0;
    private static final int FULL    = 1;

    public HashSetLinearProbing() {
        this.size = 0;
        this.buckets = (Type<E>[]) Array.newInstance(Type.class, 13);
        this.bucketStatus = new int[13];
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
        int hash = this.hash(o);
        switch (this.bucketStatus[hash]) {
            case FULL, DELETED -> {
                int index = hash;
                do {
                    if (Objects.equals(this.buckets[index], o)) return true;
                    index = (index + 1) % this.buckets.length;
                } while (index != hash);
            }
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
        return Arrays.stream(this.buckets).filter(Objects::nonNull).map(Type::data).toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) this.toArray();
    }

    @Override
    public boolean add(E e) {
        if (e == null) throw new IllegalArgumentException("Cannot add a null value.");
        int hash = this.hash(e);
        if (this.contains(e)) return false;
        if(this.size / (double) this.buckets.length > LOAD_FACTOR_THRESHOLD) {
            this.resize(this.buckets.length * 2);
        }
        switch (this.bucketStatus[hash]) {
            case EMPTY, DELETED -> {
                this.buckets[hash] = new Type<>(e);
                this.bucketStatus[hash] = FULL;
                ++this.size;
                return true;
            }
            case FULL -> {
                int i = hash;
                do {
                    if (this.bucketStatus[i] != FULL) {
                        this.buckets[i] = new Type<>(e);
                        this.bucketStatus[i] = FULL;
                        ++this.size;
                        return true;
                    }
                    i = (i + 1) % this.buckets.length;
                } while (i != hash);
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        int hash = this.hash(o);
        switch (this.bucketStatus[hash]) {
            case EMPTY -> {
                return false;
            }
            case DELETED, FULL -> {
                if (Objects.equals(this.buckets[hash], o)) {
                    this.buckets[hash] = null;
                    this.bucketStatus[hash] = DELETED;
                    --this.size;
                    return true;
                }
                int index = hash + 1;
                while (this.bucketStatus[index] != EMPTY) {
                    if (Objects.equals(this.buckets[index], o)) {
                        this.buckets[index] = null;
                        this.bucketStatus[index] = DELETED;
                        --this.size;
                        return true;
                    }
                    index = (index + 1) % this.buckets.length;
                    if (index == hash) return false;
                }
            }
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
        throw new RuntimeException("NYI");
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
        this.buckets = (Type<E>[]) Array.newInstance(Type.class, size);
        this.bucketStatus = new int[size];
    }

    private int hash(Object o) {
        return Math.abs(o.hashCode()) % this.buckets.length;
    }

    private void rehash() {
        Type<E>[] oldBuckets = this.buckets;
        this.resize(2 * oldBuckets.length);
        for (Type<E> bucket : oldBuckets) {
            if (bucket != null) this.add(bucket.data());
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(this.toArray());
    }

    private record Type<R>(R data) {

    }
}
