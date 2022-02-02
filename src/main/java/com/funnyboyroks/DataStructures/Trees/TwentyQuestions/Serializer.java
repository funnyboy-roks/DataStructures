package com.funnyboyroks.DataStructures.Trees.TwentyQuestions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

public class Serializer {

    private final File file;

    public Serializer(String file) {
        this.file = new File(file);
    }

    public Serializer(File file) {
        this.file = file;
    }

    public void serialize(BinaryTree<String> tree) {
        List<BinaryTree.Node<String>> data = tree.preOrder();
        try {
            PrintStream ps = new PrintStream(this.file);
            for (BinaryTree.Node<String> line : data) {
                ps.println(line.isLeaf() ? "A:" : "Q:");
                ps.println(line.value);
            }
            ps.close();

        } catch (FileNotFoundException ex) {
            System.out.printf("Unable to save file %s.%n", this.file.getName());
        }
    }

}