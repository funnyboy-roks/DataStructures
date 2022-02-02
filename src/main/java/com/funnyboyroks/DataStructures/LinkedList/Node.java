package com.funnyboyroks.DataStructures.LinkedList;

public class Node<T> {

    private T       value;
    private Node<T> next;

    public Node(T value) {
        this.value = value;
        this.next = null;
    }

    public T getValue() {
        return this.value;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void add(T item) {
        if (this.next == null) {
            this.next = new Node<>(item);
        } else {
            this.next.add(item);
        }
    }

    @Override
    public String toString() {
        if (this.next == null) {
            return this.value + "";
        } else {
            return this.value + ", " + this.next;
        }
    }

    public T get(int index) {
        if (index == 0) {
            return this.value;
        }
        if (this.next == null) {
            throw new IndexOutOfBoundsException();
        }
        return this.next.get(--index);
    }

    public int size() {
        return this.next == null ? 1 : this.next.size() + 1;
    }

    public boolean contains(T value) {
        return this.value == value ||
               (this.next != null && this.next.contains(value));
    }
}
