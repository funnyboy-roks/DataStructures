package com.funnyboyroks.DataStructures.LinkedList;

public class LinkedList <T> {

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();

        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        System.out.println(list);
        System.out.println(list.size());
        System.out.println(list.get(2));
    }

    private Node<T> head;

    public LinkedList() {
        this.head = null;
    }

    public void add(T item) {
        if(this.head == null) {
            this.head = new Node<>(item);
        } else {
            this.head.add(item);
        }
    }

    public T get(int index) {
        return head == null ? null : head.get(index);
    }

    public int size() {
        return head == null ? 0 : head.size();
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public boolean contains(T value) {
        return this.head != null && this.head.contains(value);
    }

//    public T[] toArray(T[] a) {
//        a = Arrays.stream(a).filter((t) -> false).toArray(T[]:new);
//    }

    @Override
    public String toString() {
        return "[" + head + "]";
    }
}
