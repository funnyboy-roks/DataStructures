package com.funnyboyroks.DataStructures.Trees;

import java.util.*;

public class BinarySearchTree<T> {

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(Integer::compare);

        for (int i = 0; i < 10; i++) {
            int n = (int) (Math.random() * 100);
            System.out.println(n + " - " + tree.add(n));
        }
        System.out.println(tree.root.values());
        System.out.println(tree);
    }

    public       Node<T>       root;
    public final Comparator<T> comparator;

    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
    }

    private void addAll(Collection<T> values) {
        values.forEach(this::add);
    }

    public boolean add(T value) {
        if (this.root == null) {
            this.root = new Node<>(value);
            return true;
        }
        return this.root.add(value, comparator);
    }


    public int getSize() { // This is more reliable than having a running count.  Sure, it's O(n), but idrc
        if (this.root == null) return 0; // Empty if root doesn't exist
        return this.root.size(); // Return the size of the root
    }

    public List<T> values() {
        return this.root == null ? Collections.emptyList() : this.root.values();
    }

    /**
     * Checks if tree contains the value, using the comparator
     */
    public boolean contains(T value) {
        if (this.root == null) return false; // Can't contain any values if there's no data
        return this.root.contains(value, comparator);
    }

    /**
     * Clear the tree completely
     *
     * @return If anything was removed
     */
    public boolean clear() {
        boolean r = this.root != null;
        this.root = null;
        return r;
    }

    /**
     * Remove an item, using the comparator provided
     *
     * @return if the item existed
     */
    public boolean remove(T item) {
        if (this.root == null) return false; // Can't remove anything if empty
        if (this.root.value.equals(item)) {
            List<T> data = this.root.values().stream().filter(n -> !n.equals(item)).toList();
            this.root = null;
            this.addAll(data);
            return true;
        }

        return this.root.remove(item, this);
    }

    @Override
    public String toString() {
        return this.values().toString();
    }

    public static class Node<T> {

        public T       value;
        public Node<T> left;
        public Node<T> right;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public boolean add(T item, Comparator<T> comparator) {
            int comp = comparator.compare(item, this.value); // Get the comparison value between the values
            if (comp == 0) return false; // Already exists in tree

            Node<T> node = new Node<>(item);
            if (comp > 0) { // If the value should go to the right
                if (this.right == null) { // If the right child doesn't exist
                    this.right = node; // Set it to the new value
                    return true; // return that it's been added properly
                }
                return this.right.add(item, comparator); // Run the add method on the next child
            }

            if (this.left == null) { // If the left child doesn't exist
                this.left = node; // Set it to the new value
                return true; // return that it's been added
            }
            return this.left.add(item, comparator); // Run the add method on the next child
        }

        public int size() {
            int sum = 1; // 1 for the node itself
            if (this.left != null) sum += this.left.size(); // If the left side exists, add the sum of its nodes
            if (this.right != null) sum += this.right.size(); // If the right side exists, add the sum of its nodes
            return sum;
        }

        public boolean contains(T item, Comparator<T> comparator) {
            int comp = comparator.compare(item, this.value); // Get the comparison
            if (comp == 0) return true; // 0 means they're the same value
            Node<T> side = comp > 0 ? this.right : this.left; // Get the side to check next
            if (side == null) return false; // That side is empty
            return side.contains(item, comparator); // Compare the next branch down
        }

        public List<T> values() {
            List<T> set = new ArrayList<>();
            if (this.left != null) set.addAll(this.left.values()); // Add all values on the left side
            set.add(this.value);
            if (this.right != null) set.addAll(this.right.values()); // Add all values on the right side
            return set;
        }

        public boolean remove(T item, Comparator<T> comparator) {
//            int valueComp = comparator.compare(item, this.value);
//            if(valueComp == 0) { // remove this node (ish)
//                this.value = null // Yeet this value
//                this.value = this.right == null ? this.left
//            }
            return true;
        }

        public T getLeast() {
            if (this.left == null) return this.value;
            return this.left.getLeast();
        }

        public T getMost() {
            if (this.right == null) return this.value;
            return this.right.getMost();
        }

        @Deprecated
        public boolean remove(T value, BinarySearchTree<T> tree) {
            if (this.value.equals(value)) {
                List<T> values = this
                    .values()
                    .stream()
                    .filter(n -> !n.equals(value))
                    .toList();
                tree.addAll(values);
                return true;
            }
            if (this.left != null) {
                if (this.left.value.equals(value)) {
                    List<T> values = this.left
                        .values()
                        .stream()
                        .filter(n -> !n.equals(value)) // I'm too lazy to do this properly :P
                        .toList();

                    this.left = null;
                    tree.addAll(values);
                    return true;
                }
                if (this.left.remove(value, tree)) {
                    return true;
                }
            }
            if (this.right != null) {
                if (this.right.value.equals(value)) {
                    List<T> values = this.right
                        .values()
                        .stream()
                        .filter(n -> !n.equals(value)) // I'm too lazy to do this properly :P
                        .toList();

                    this.right = null;
                    tree.addAll(values);
                    return true;
                }
                return this.right.remove(value, tree);
            }
            return false;
        }

    }

}
