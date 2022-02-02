package com.funnyboyroks.DataStructures.Trees;

import java.util.stream.Collectors;

/**
 * Integer implementation for class lab
 */
public class BinarySearchIntTree extends BinarySearchTree<Integer> {

    public static void main(String[] args) {
        BinarySearchIntTree tree = new BinarySearchIntTree();

        for (int i = 0; i < 10; i++) {
            int n = (int) (Math.random() * 100);
            System.out.println(n + " - " + tree.add(n));
        }
//        System.out.println(tree.root.values());
        System.out.println(tree);
    }

    public BinarySearchIntTree() {
        super(Integer::compare);
    }

    public BinarySearchIntTree(int value) {
        this();
        this.add(value);
    }

    @Override
    public String toString() {
        return this
            .values()
            .stream()
            .map(String::valueOf)
            .collect(Collectors.joining(" "));
    }
}
