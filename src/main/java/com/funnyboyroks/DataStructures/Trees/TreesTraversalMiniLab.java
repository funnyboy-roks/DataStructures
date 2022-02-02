package com.funnyboyroks.DataStructures.Trees;

public class TreesTraversalMiniLab {

    public static void main(String[] args) {
        // Creating the Tree
        IntTreeNode overallRoot = new IntTreeNode(17);
        overallRoot.left = new IntTreeNode(41, new IntTreeNode(29), new IntTreeNode(6));
        overallRoot.right = new IntTreeNode(9, new IntTreeNode(81), new IntTreeNode(40));

        // CALL the 3 print methods using the overallRoot
        // Hrmm, intersting, they're not static :thonk:
        var lab = new TreesTraversalMiniLab();
        lab.printPreOrder(overallRoot);
        System.out.println(" --- ");
        lab.printInOrder(overallRoot);
        System.out.println(" --- ");
        lab.printPostOrder(overallRoot);
    }

    public void printPreOrder(IntTreeNode root) {
        System.out.println(root.data);
        if (root.left != null) {
            printPreOrder(root.left);
        }
        if (root.right != null) {
            printPreOrder(root.right);
        }
    }

    public void printInOrder(IntTreeNode root) {
        if (root.left != null) {
            printPreOrder(root.left);
        }
        System.out.println(root.data);
        if (root.right != null) {
            printPreOrder(root.right);
        }

    }

    public void printPostOrder(IntTreeNode root) {
        if (root.left != null) {
            printPreOrder(root.left);
        }
        System.out.println(root.data);
        if (root.right != null) {
            printPreOrder(root.right);
        }
    }

}


class IntTreeNode {

    public int         data; // data stored at this node
    public IntTreeNode left; // reference to left subtree
    public IntTreeNode right; // reference to right subtree

    // Constructs a leaf node with the given data.
    public IntTreeNode(int data) {
        this(data, null, null);
    }

    // Constructs a branch node with the given data and links.
    public IntTreeNode(int data, IntTreeNode left,
                       IntTreeNode right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
}