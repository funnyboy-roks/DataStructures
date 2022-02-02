package com.funnyboyroks.DataStructures.Trees.TwentyQuestions;

import com.funnyboyroks.DataStructures.Trees.TwentyQuestions.BinaryTree.Node;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Deserializer {

    private final File               file;
    private       Iterator<GameData> linesIterator; // lines iterator to help with recursion

    public Deserializer(String path) {
        this(new File(path));
    }

    public Deserializer(File file) {
        this.file = file;
    }

    public BinaryTree<String> deserialize() throws IOException {
        if (!this.file.exists()) {
            this.file.createNewFile();
            return null;
        }
        List<GameData> lines = this.getLines();
        System.out.println(lines);
        if (lines == null) return null;
        this.linesIterator = lines.iterator();
        BinaryTree<String> tree = new BinaryTree<>(this.linesIterator.next().value);
        this.generateTree(tree.root);
        return tree;
    }

    public BinaryTree<String> deserializeCatch() {
        try {
            return this.deserialize();
        } catch (Exception ex) {
            System.out.println("Error loading file " + this.file.getName());
            ex.printStackTrace();
            return null;
        }
    }

    private void generateTree(Node<String> node) {
        try {
            GameData next = this.linesIterator.next();
            System.out.println(next);
            node.left = new Node<>(next.value);
            if (!next.leaf) {
                this.generateTree(node.left);
            }
            next = this.linesIterator.next();
            System.out.println(next);
            node.right = new Node<>(next.value);
            if (!next.leaf) {
                this.generateTree(node.right);
            }
        } catch (NoSuchElementException ex) {
            throw new IllegalArgumentException("Invalid Tree Structure in File!", ex);
        }
    }

    private List<GameData> getLines() {
        try {
            // One-liner, why not :)
            return Files.readString(this.file.toPath())
                .replaceAll("([QA]):[\n\r]+", "$1:§") // Convert from the format of A:\nValue to A:§Value
                .lines()
                .map(l -> new GameData(l.substring(l.indexOf('§') + 1), l.toUpperCase().startsWith("A:")))
                .toList();
        } catch (IOException ex) {
            System.out.printf("Unable to load file %s.%n", this.file.getName());
            ex.printStackTrace();
            return null;
        }
    }

    public record GameData(String value, boolean leaf) {

        @Override
        public String toString() {
            return (this.leaf ? "A: " : "Q: ") + this.value;
        }
    }
}
