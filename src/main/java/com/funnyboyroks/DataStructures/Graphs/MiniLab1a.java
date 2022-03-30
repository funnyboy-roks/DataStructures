package com.funnyboyroks.DataStructures.Graphs;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Rewritten to be better :P
 */
public class MiniLab1a {

    final static int numOfVertices = 10;

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(new File("edgesB.txt"));
        List<Edge> edges = new ArrayList<>();
        while (scanner.hasNextLine()) edges.add(Edge.from(scanner.nextLine()));

        printAdjMatrix(edges);
        printAdjList(edges);
    }

    static void printAdjMatrix(List<Edge> edges) {
        int[][] mat = new int[numOfVertices][numOfVertices];
        for (Edge edge : edges) {
            ++mat[edge.from][edge.to];
        }
        System.out.println(
            Arrays.deepToString(mat)
                .replaceAll(",|(\\s+)?\n(\\s+)?", "]\n")
                .replaceAll("[\\[\\],]+", "")
                .replaceAll("0", ".")
                .replaceAll("1", "✔")
                .trim()
        );
    }

    static void printAdjList(List<Edge> edges) {
        Map<Integer, List<Integer>> adjList = new HashMap<>();

        for (Edge edge : edges) {
            adjList.putIfAbsent(edge.from, new ArrayList<>());
            adjList.get(edge.from).add(edge.to);
        }

        String out = adjList
            .entrySet()
            .stream()
            .map(entry -> String.format("%s: %s", entry.getKey(), entry.getValue().stream().map(Object::toString).collect(Collectors.joining(" -> "))))
            .collect(Collectors.joining("\n"));

        System.out.println(out);

    }

    /**
     * So edgy lmao
     */
    private record Edge(int from, int to) {

        public static Edge from(String value) {
            int[] parts = Arrays.stream(value.split(",")).mapToInt(Integer::parseInt).toArray();
            return new Edge(parts[0], parts[1]);
        }

    }

}
