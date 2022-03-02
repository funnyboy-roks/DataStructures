package com.funnyboyroks.DataStructures.Maps.SpanishDict;

import com.funnyboyroks.DataStructures.Maps.TreeMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final boolean SAME_DIR = true; // Make sure this is true when testing in the same directory

    private static final String PATH = SAME_DIR ? "" : "src/main/java/" + Main.class.getPackageName().replace(".", "/") + "/";

    public static void main(String[] args) throws FileNotFoundException {
        Map<String, String> dict = new TreeMap<>();
        List<String> lines = new ArrayList<>();
        load(dict);
        load(lines);

        System.out.println(dict);

        lines.stream()
            .map(line -> line.split(" "))
            .map(arr -> Arrays.stream(arr)
                .map(dict::get).collect(Collectors.joining(" "))
            )
            .forEach(System.out::println);
    }

    public static void load(Map<String, String> dict) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(PATH + "dictionary.dat"));
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("/");
            dict.put(line[0], line[1]);
        }
    }

    public static void load(List<String> set) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(PATH + "spanish.dat"));
        while (scanner.hasNextLine()) {
            set.add(scanner.nextLine());
        }
    }

}
