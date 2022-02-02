package com.funnyboyroks.DataStructures.Queue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A full implementation of the {@link java.util.Queue} interface
 *
 * @param <T> The type of data for the class to store.
 */
public class Queue<T> implements java.util.Queue<T> {

    public static void main(String[] args) {
        Queue<String> testQ = new Queue<>("a");
        System.out.println(testQ.toString2()); // a
        testQ.enqueue("b");
        System.out.println(testQ.toString2()); // a | b
        System.out.println(testQ.dequeue());   // a
        System.out.println(testQ.toString2()); // b
        System.out.println(testQ.isEmpty());   // false
        testQ.enqueue("c");
        testQ.enqueue("d");
        testQ.enqueue("e");
        System.out.println(testQ.toString2()); // b | c | d | e
        testQ.sendToBack();
        System.out.println(testQ.toString2()); // c | d | e | b
        System.out.println(testQ.size());      // 4

    }

    protected Node<T> head     = null;
    protected Node<T> tail     = null;
    protected int     size     = 0;
    protected int     capacity = -1;

    public Queue(int capacity) {
        this.capacity = capacity;
    }

    public Queue(Collection<T> items) {
        this.addAll(items);
    }

    public Queue(T... items) { // Close enough to the required `Queue(T item)` -- Works with that
        Collections.addAll(this, items); // Can be done with a for loop, if wanted
    }

    /**
     * Set the maximum capacity of the Queue
     *
     * @param capacity The new capacity
     * @return If the current size is greater than the new capacity
     */
    public boolean setCapacity(int capacity) {
        this.capacity = capacity;
        return this.size > this.capacity;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0 || this.head == null || this.tail == null;
    }

    @Override
    public boolean contains(Object o) {
        for (T t : this) { // I made an iterator, might as well use it :P
            if (t.equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator<>(this);
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[this.size];
        for (int i = 0; i < this.size(); i++) {
            arr[i] = this.get(i);
        }
        return arr;
    }

    @Override
    public <T1> T1[] toArray(T1[] t1s) {
        T1[] arr = Arrays.copyOf(t1s, this.size);
        for (int i = 0; i < this.size(); i++) {
            arr[i] = (T1) this.get(i);
        }
        return arr;
    }

    protected T get(int index) {
        return this.getNode(index).getValue();
    }

    @Override
    public boolean add(T item) {
        if (this.capacity == size++ && this.capacity > -1) {
            throw new IllegalStateException("Queue capacity is full.");
        }
        if (this.head == null) {
            this.head = this.tail = new Node<>(item);
            return true;
        }
        // TODO: "simplify" this
        Node<T> n = new Node<>(item);
        ((this.tail = (n.head = this.tail).tail = n).tail = this.head).head = n;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < this.size(); i++) {
            Node<T> n = this.getNode(i);
            if (n.getValue().equals(o)) {
                Node<T> prev = this.getNode(i - 1);
                Node<T> next = this.getNode(i + 1);
                (prev.tail = next).head = prev; // prev.tail = next and next.head = prev
                if (n == this.head) {
                    this.remove();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return collection.stream().map(this::contains).reduce(true, Boolean::logicalAnd);
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        collection.forEach(this::add);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        Queue<T> original = this.copy();
        this.clear();
        for (T t : original) {
            if (!collection.contains(t)) {
                this.add(t);
            }
        }
        return original.size() != this.size();
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        Queue<T> original = this.copy();
        this.clear();
        for (T t : original) {
            if (collection.contains(t)) {
                this.add(t);
            }
        }
        return original.size() != this.size();
    }

    @Override
    public void clear() {
        this.head = this.tail = null;
    }

    @Override
    public boolean offer(T t) {
        return this.add(t);
    }

    /**
     * Remove the first item from the Queue
     *
     * @return The value removed
     */
    @Override
    public T remove() {
        if (this.size <= 0) throw new IndexOutOfBoundsException();
        T o = this.head.getValue();
        if (this.size == 1) {
            this.size = 0;
            this.head = this.tail = null;
            return o;
        }
        --this.size;
        (this.tail.tail = this.head = this.head.tail).head = this.tail;
        return o;
    }

    @Override
    public T poll() {
        return this.remove();
    }

    @Override
    public T element() {
        return this.peek();
    }

    @Override
    public T peek() {
        return this.tail == null ? null : this.tail.getValue();
    }

    protected Node<T> getNode(int index) {
        Node<T> node = this.head;
        while (index > 0) {
            node = node.tail;
            --index;
        }
        return node;
    }

    @Override
    public String toString() {
        // Streams FTW
        return "[" + this.stream().map(T::toString).collect(Collectors.joining(", ")) + "]";
    }

    public Queue<T> copy() {
        Queue<T> q = new Queue<>();
        Node<T> node = this.head;
        int i = 0;
        while (node != null && i < size) { // Can't use iterable, since used in QueueIterator
            q.add(node.getValue());
            node = node.tail;
            ++i;
        }
        return q;
    }

    /**
     * Methods specifically for assignment -- mostly just an alias for another method
     */

    public boolean enqueue(T item) {
        return this.offer(item);
    }

    public T dequeue() {
        return this.poll();
    }

    /**
     * Rotate the items in the queue by one
     *
     * @return if there were enough items to rotate
     */
    public boolean sendToBack() {
        if (this.isEmpty() || this.size == 1) return false;
        this.tail = this.head;
        this.head = this.head.tail;
        return true;
    }

    public String toString2() {
        return this.stream().map(T::toString).collect(Collectors.joining(" | "));
    }

    private static class Node<T> { // TODO: Convert to record (If I feel like it :P)

        private final T       value;
        private       Node<T> head;
        private       Node<T> tail;

        public Node(T value) {
            this.value = value;
            this.head = null;
            this.tail = null;
        }

        public T getValue() {
            return value;
        }
    }

    private record QueueIterator<T>(Queue<T> data) implements Iterator<T> {

        private QueueIterator(Queue<T> data) {
            this.data = data.copy(); // Clone it
        }

        @Override
        public boolean hasNext() {
            return !data.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new RuntimeException("No next value");
            }
            return data.poll();
        }
    }
}
