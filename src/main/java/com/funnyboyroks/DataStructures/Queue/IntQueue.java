package com.funnyboyroks.DataStructures.IntQueue;

public class IntQueue {

    public static void main(String[] args) {
        IntQueue intQueue = new IntQueue();
        intQueue.enqueue(1);
        intQueue.enqueue(2);
        intQueue.enqueue(3);
        intQueue.enqueue(4);
        intQueue.enqueue(5);
        intQueue.printQueue(); // 1 2 3 4 5
        System.out.println(intQueue.size()); // 5
        System.out.println(intQueue.isEmpty()); // false
        System.out.println(intQueue.dequeue().getData()); // 1
    }

    private Node head;
    private Node tail;
    private int size;

    public IntQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void enqueue(int data) {
        Node node = new Node(data);
        if (this.head == null) {
            this.head = node;
            this.tail = node;
        } else {
            this.tail.setNext(node);
            this.tail = node;
        }
        this.size++;
    }

    public Node dequeue() {
        if (this.head == null) {
            return null;
        }
        Node node = this.head;
        this.head = this.head.getNext();
        this.size--;
        return node;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void printQueue() {
        Node node = this.head;
        while (node != null) {
            System.out.print(node.getData() + " ");
            node = node.getNext();
        }
        System.out.println();
    }

    public Node getHead() {
        return this.head;
    }

    public Node getTail() {
        return this.tail;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getNext() {
        return this.next;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getData() {
        return this.data;
    }

    private Node next;
    private int data;

    public class Node {

        private Node next;
        private int data;

        public Node(int data) {
            this.next = null;
            this.data = data;
        }

        public Node getNext() {
            return this.next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public int getData() {
            return this.data;
        }

        public void setData(int data) {
            this.data = data;
        }

    }

}
