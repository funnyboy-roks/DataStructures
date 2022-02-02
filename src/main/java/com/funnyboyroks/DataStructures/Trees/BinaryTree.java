package com.funnyboyroks.DataStructures.Trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BinaryTree<T> {

    public static void main(String[] args) {
        Node<Integer> root = new Node<>(
            new Node<>(
                new Node<>(
                    new Node<>(1),
                    new Node<>(2),
                    3
                ),
                new Node<>(6),
                5
            ),
            new Node<>(23),
            1
        );

        BinaryTree<Integer> tree = new BinaryTree<>(17);
        tree.root().left(new Node<>(
            new Node<>(29),
            new Node<>(6),
            41
        ));
        tree.root().right(new Node<>(
            new Node<>(81),
            new Node<>(40),
            9
        ));

//        IntTreeNode overallRoot = new IntTreeNode(17);
//        overallRoot.left = new IntTreeNode(41, new IntTreeNode(29), new IntTreeNode(6));
//        overallRoot.right = new IntTreeNode(9, new IntTreeNode(81), new IntTreeNode(40));

//        BinaryTree<Integer> bt = new BinaryTree<>();
//        bt.root(root);
//        System.out.println(bt.preOrderTransversal());
//        System.out.println(bt.inOrderTransversal());
//        System.out.println(bt.postOrderTransversal());
//        System.out.println(bt);
//
//        System.out.println();
//        System.out.println();
//        System.out.println();
//
//        System.out.println(bt);
        System.out.println(tree.preOrderTransversal());
        System.out.println(tree.inOrderTransversal());
        System.out.println(tree.postOrderTransversal());
        System.out.println(tree);
    }

    private Node<T> root;

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(T value) {
        this.root = new Node<>(value);
    }

    public BinaryTree(Node<T> root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return String.join("\n", this.root.toStringList(0));
    }

    public void root(Node<T> node) {
        root = node;
    }

    public Node<T> root() {
        return root;
    }

    public List<T> preOrderTransversal() {
        return this.root.preOrderTransversal().stream().map(Node::value).collect(Collectors.toList());
    }

    public List<T> inOrderTransversal() {
        return this.root.inOrderTransversal().stream().map(Node::value).collect(Collectors.toList());
    }

    public List<T> postOrderTransversal() {
        return this.root.postOrderTransversal().stream().map(Node::value).collect(Collectors.toList());
    }


    private static final class Node<T> {

        private Node<T> left;
        private Node<T> right;
        private T       value;

        public Node(Node<T> left, Node<T> right, T value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }

        public Node(T value) {
            this(null, null, value);
        }

        public Node<T> left() {
            return left;
        }

        public Node<T> right() {
            return right;
        }

        public T value() {
            return value;
        }

        public void left(Node<T> v) {
            left = v;
        }

        public void right(Node<T> v) {
            right = v;
        }

        public void value(T v) {
            value = v;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Node) obj;
            return Objects.equals(this.left, that.left) &&
                   Objects.equals(this.right, that.right) &&
                   Objects.equals(this.value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right, value);
        }

//        @Override
//        public String toString() {
//            if (this.left() == null && this.right() == null) {
//                return this.value + "";
//            }
//            return "(" + this.left() + ", " + this.right() + ")";
//        }


        @Override
        public String toString() {
            return String.join("\n", this.toStringList(0));
        }

        public List<Node<T>> preOrderTransversal() {
            List<Node<T>> l = new ArrayList<>();
            l.add(this);
            if (this.left != null) {
                l.addAll(this.left.preOrderTransversal());
            }
            if (this.right != null) {
                l.addAll(this.right.preOrderTransversal());
            }
            return l;
        }

        public List<Node<T>> inOrderTransversal() {
            List<Node<T>> l = new ArrayList<>();
            if (this.left != null) {
                l.addAll(this.left.inOrderTransversal());
            }
            l.add(this);
            if (this.right != null) {
                l.addAll(this.right.inOrderTransversal());
            }
            return l;
        }

        public List<Node<T>> postOrderTransversal() {
            List<Node<T>> l = new ArrayList<>();
            if (this.left != null) {
                l.addAll(this.left.postOrderTransversal());
            }
            if (this.right != null) {
                l.addAll(this.right.postOrderTransversal());
            }
            l.add(this);
            return l;
        }

        public List<String> toStringList(int indentation) {
            List<String> values = new ArrayList<>();
            String indent = "  ".repeat(Math.max(0, indentation - 1));
            if (indentation != 0) {
                indent += "- ";

            }
            values.add(indent + this.value);
            indentation++;
            if (this.left() != null) {
                values.addAll(this.left().toStringList(indentation));
            }
            if (this.right() != null) {
                values.addAll(this.right().toStringList(indentation));
            }
            return values.stream().filter(s -> !s.isBlank()).toList();
        }


    }

}
