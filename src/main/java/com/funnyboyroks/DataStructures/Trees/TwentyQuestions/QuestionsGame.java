package com.funnyboyroks.DataStructures.Trees.TwentyQuestions;

import com.funnyboyroks.DataStructures.Trees.TwentyQuestions.BinaryTree.Node;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class QuestionsGame {

    private static final List<String> YES = Stream.of("yes", "yeah", "y", "yep", "aye", "true", "t").map(String::toLowerCase).toList();
    private static final List<String> NO  = Stream.of("no", "n", "nope", "nay", "false", "f").map(String::toLowerCase).toList();

    private final Serializer         serializer;
    private final Deserializer       deserializer;
    private final Scanner            userInput;
    private       BinaryTree<String> tree;

    public QuestionsGame(String filePath) {
        this.userInput = new Scanner(System.in);
        this.serializer = new Serializer(filePath);
        this.deserializer = new Deserializer(filePath);
        this.tree = this.deserializer.deserializeCatch();
        if (this.tree == null) {
            this.tree = new BinaryTree<>(this.getInitialObject());
        }
    }

    public void play() {
        List<Node<String>> preOrder = this.tree.preOrder();
        long leaves = preOrder.stream().filter(Node::isLeaf).count();
        System.out.printf(
            "Tree Info:%n\tHeight: %d%n\tNodes: %d%n\tQuestions: %d%n\tAnswers: %d%n",
            this.tree.getHeight(),
            preOrder.size(),
            preOrder.size() - leaves,
            leaves
        );

        Node<String> current = this.tree.root;
        boolean wrong = !this.confirm("I guess %s, is this correct?", current.value);
        if (wrong) {
            String obj = this.prompt("What object were you thinking?");
            if (obj.equalsIgnoreCase(current.value)) {
                System.out.println("That's what I said!");
                return;
            }
            String question = this.prompt("What question should be asked to tell them apart?");
            boolean answer = this.confirm("To get to %s, would the player answer yes or no to %s", obj, question);
            String currentVal = current.value;
            current.value = question;
            if (answer) {
                current.left = new Node<>(obj);
                current.right = new Node<>(currentVal);
            } else {
                current.left = new Node<>(currentVal);
                current.right = new Node<>(obj);
            }
            System.out.printf("Updated the tree to ask %s, with Yes: '%s' and No: '%s'%n", question, current.left.value, current.right.value);
        } else {
            System.out.println("Nice!");
        }
    }

    public void save() {
        this.serializer.serialize(this.tree);
    }

    public String getInitialObject() {
        System.out.println("There are no objects to guess in that questions file.");
        return this.prompt("Can you provide me with an initial object?");
    }

    public boolean confirm(String str, Object... replacements) {
        String s = this.prompt(str, replacements);
        int attempts = 5;
        while (--attempts > 0) {
            if (YES.contains(s)) {
                return true;
            } else if (NO.contains(s)) {
                return false;
            }
            System.out.printf("Invalid value (%d attempts remaining). Options: Yes: %s, No: %s%n", attempts, YES, NO);
            s = this.prompt(str, replacements);
        }
        return false;
    }

    public String prompt(String str, Object... replacements) {
        str = str.formatted(replacements);

        System.out.printf("%s%n> ", str);
        return this.userInput.nextLine();
    }
}
