package com.funnyboyroks.DataStructures.Graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MiniLab2 {

    static final int                         numOfVertices = 10;
    static       Map<Integer, List<Integer>> adjList;

    public static void main(String[] args) throws FileNotFoundException {
        adjList = new HashMap<>();

        for (int x = 0; x < numOfVertices; x++) {
            adjList.put(x, new ArrayList<>()); // This makes me sad :(
        }

        Scanner scanner = new Scanner(new File("edgesB.txt"));

        while (scanner.hasNextLine()) {
            int[] parts = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
            adjList.get(parts[0]).add(parts[1]);
            adjList.get(parts[1]).sort(Comparator.naturalOrder());
        }

        System.out.println("Depth First: " + depthFirstTraversal());
        System.out.println("Breadth First: " + breadthFirstTraversal());
    }

    public static List<Integer> depthFirstTraversal() {
        List<Integer> out = new ArrayList<>();
        dft(0, new boolean[numOfVertices], out);
        return out;
    }

    private static void dft(int vertex, boolean[] visited, List<Integer> out) {
        visited[vertex] = true;
        out.add(vertex);
        adjList.get(vertex).forEach(u -> {
            if (!visited[u]) {
                dft(u, visited, out);
            }
        });
    }

    public static List<Integer> breadthFirstTraversal() {
        Queue<Integer> q = new LinkedList<>();
        List<Integer> out = new ArrayList<>();
        boolean[] visited = new boolean[numOfVertices];

        q.offer(0); // Add the first item to the queue
        visited[0] = true; // Mark it as visited

        while (!q.isEmpty()) {
            int vertex = q.poll(); // Get & Remove the first item from the queue
            out.add(vertex);
            adjList.get(vertex).forEach(u -> {
                if (!visited[u]) {
                    q.offer(u);
                    visited[u] = true;
                }
            });
        }

        return out;
    }

}
