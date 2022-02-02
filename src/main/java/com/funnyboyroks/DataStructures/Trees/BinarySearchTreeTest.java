package com.funnyboyroks.DataStructures.Trees;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

class BinarySearchTreeTest {

    public static void main(String[] args) {
        BinarySearchTreeTest.test();
    }

    public static void test() {
        BinarySearchIntTree tree = new BinarySearchIntTree();

        // Populating the Tree
        System.out.println("Adding values to Tree:");
        int[] initialValues = { 18, 12, 35, 4, 15, 22, 58, -2, 7, 13, 16, 19, 31, 40, 87 };
        for (int value : initialValues) {
            System.out.printf("\tAdding% 3d: ", value);
            attemptBoolean(u -> tree.add(value), true);
        }

        // Testing 1 aspect of size
        System.out.print("\nTest initial size: ");
        attemptInt(u -> tree.getSize(), initialValues.length);

        // Printing a visual of the tree
        System.out.println("\nPrinting tree: ");
        try {
            System.out.println(traversePreOrder(tree.root));
        } catch (Exception e) {
            System.out.println("FAILED -- " + e);
        }

        // Printing the tree as a list of numbers (in-order)
        System.out.println("\nDisplay all value in the tree using the toString() method: ");
        try {
            String treeS = "" + tree;
            System.out.println("\t" + treeS);
            assertEquals(
                treeS,
                Arrays.stream(initialValues)
                    .sorted()
                    .mapToObj(Integer::toString)
                    .collect(Collectors.joining(" "))
            );
        } catch (Exception e) {
            System.out.println("FAILED - Got " + e);
        }

        System.out.println();
        // Checking to ensure the numbers are in numerical order
        System.out.print("Checking if items are stored in order: ");
        try {
            int[] inOrder = Arrays.stream(tree.toString().split(" ")).mapToInt(Integer::parseInt).toArray();
            int prev = inOrder[0];
            for (int j = 1; j < inOrder.length; j++) {
                if (inOrder[j] < prev) {
                    throw new RuntimeException("Values are not in order."); // Break out of the try block
                }
                prev = inOrder[j];
            }
            System.out.println("Success"); // Only gets here if everything is in order
        } catch (Exception e) {
            System.out.println("Failed -- " + e);
        }

        System.out.println();

        // Testing the remove method
        System.out.println("Remove Test: ");
        // Node name, Node value, Expected result
        Object[][] removeTestCases = {
            { "Leaf", -2, true },
            { "Branch", 15, true },
            { "Branch", 22, true },
            { "Branch", 12, true },
            { "Root", 18, true },
            { "Branch", 17, false },

            };

        for (Object[] removeTestCase : removeTestCases) {
            String name = pad(
                removeTestCase[0] + "",
                ' ',
                Arrays.stream(removeTestCases)
                    .map(c -> (String) c[0])
                    .mapToInt(String::length).max().getAsInt(),
                false
            );
            int value = (int) removeTestCase[1];
            boolean expected = (boolean) removeTestCase[2];

            System.out.printf("\tRemove %s Node [%s]: ", name, value);
            boolean remove = attemptCheck(u -> tree.remove(value), expected, true);
            if (remove) {
                Boolean val = attemptBooleanReturn(u -> tree.contains(value), false);
                if (val == null || !val) {
                    System.out.printf("%s still in tree.%n", value);
                } else {
                    System.out.println("Passed");
                }

            } else {
                System.out.println("Failed");
            }
        }

        System.out.println();

        //Size Test, part 2
        System.out.println("Test Size: ");
        System.out.print("\tRemove Update Size: ");
        int sizeBefore;
        try {
            sizeBefore = tree.getSize();
            tree.remove(40);
            System.out.println(sizeBefore - 1 == tree.getSize());
        } catch (Exception e) {
            System.out.println("Failed -- " + e);
        }
    }

    public static void attemptBoolean(Function<Integer, Boolean> fn, boolean expected) {
        try {
            boolean val = fn.apply(0);
            System.out.println(val == expected ? "Passed" : "Failed, returned: " + val);
        } catch (Exception e) {
            System.out.println("Failed -- " + e);
            e.printStackTrace();
        }

    }

    public static Boolean attemptBooleanReturn(Function<Integer, Boolean> fn, boolean expected) {
        try {
            return fn.apply(0) == expected;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> boolean attemptCheck(Function<Integer, T> fn, T expected, boolean printStack) {
        try {
            return expected.equals(fn.apply(0));
        } catch (Exception e) {
            if (printStack) e.printStackTrace();
            return false;
        }
    }

    public static void attemptInt(Function<Integer, Integer> fn, int expected) {
        try {
            int val = fn.apply(0);
            System.out.println(val == expected ? "Passed" : "Failed, returned: " + val);
        } catch (Exception e) {
            System.out.println("Failed -- " + e);
            e.printStackTrace();
        }

    }

    public static <T> T safeRun(Function<Integer, T> run, boolean printStack) {
        try {
            return run.apply(0);
        } catch (Exception e) {
            if (printStack) e.printStackTrace();
            return null;
        }
    }

    public static void assertEquals(Object a, Object b) {
        if (!Objects.equals(a, b)) {
            throw new AssertionError(a + " is not equal to " + b);
        }
    }

    public static String traversePreOrder(BinarySearchTree.Node<Integer> root) {

        if (root == null) return "";

        StringBuilder sb = new StringBuilder("" + root.value);

        traverseNodes(sb, "", root.left, root.right != null);
        traverseNodes(sb, "", root.right, false);

        return sb.toString().trim();
    }

    private static void traverseNodes(StringBuilder sb, String padding, BinarySearchTree.Node<Integer> node, boolean hasRightSibling) {
        if (node == null) return; // Early Return FTW

        sb.append("\n")
            .append(padding)
            .append("+——")
            .append(String.format("% 3d", node.value));

        String paddingForBoth = padding + (hasRightSibling ? "|   " : "    ");

        traverseNodes(sb, paddingForBoth, node.left, node.right != null);
        traverseNodes(sb, paddingForBoth, node.right, false);

    }

    public static String pad(String str, char filler, int width, boolean start) {
        if (str.length() > width) return str.substring(0, width);
        String add = "";
        for (int i = 0; i < width - str.length(); ++i) {
            add += filler;
        }
        return start ? add + str : str + add;
    }
}