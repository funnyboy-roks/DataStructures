package com.funnyboyroks.DataStructures.Graphs;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*************************************
 * Java Built-in Linked List Class:
 *
 *	-Constructor Examples
 *		LinkedList<String> list = new LinkedList<String>();
 *		LinkedList list2 = new LinkedList();
 *
 *	-Add Example:
 *		list.add("red");
 *
 *	-Some Relevant Methods:
 *		add(E e)
 *		add(int index, E element)
 *		addFirst()
 *		addLast()
 *		removeFirst()
 *		removeLast()
 *
 *		element() - Retrieves, but does not remove, the head (first element) of this list.
 *		getFirst() - Returns the first element in this list.
 *		getLast() - Returns the last element in this list.
 *		get(int index) - Returns the element at the specified position in this list.
 *
 *		set(int index, E element) - Replaces the element at the specified position in this list with the specified element.
 *
 *		size() - Returns the number of elements in this list
 *
 *************************************/

public class MiniLab1 {

    final static int numOfVertices = 10;

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(new File("edgesA.txt"));
        List<String> linesList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            linesList.add(scanner.nextLine());
        }
        String[] lines = linesList.toArray(String[]::new);

        printAdjMatrix(lines);
        printAdjList(lines);
    }

    static void printAdjMatrix(String[] edgeList) {
        /* Print an adjacency matrix given
         * 	a list of a Graph's edges.
         */
        int[][] mat = new int[numOfVertices][numOfVertices];
        for (String s : edgeList) {
            int[] parts = Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray();
            ++mat[parts[0]][parts[1]];
        }
        System.out.println(Arrays.deepToString(mat).replaceAll("],", "]\n").trim());

    }

    static void printAdjList(String[] edgeList) {
        /* Print an adjacency list given
         * 	a list of a Graph's edges.
         *
         *	-Don't worry about sorting the Linked Lists
         */

        LinkedList[] adjList = new LinkedList[numOfVertices]; // This makes me sad :(

        for (int x = 0; x < adjList.length; x++) {
            adjList[x] = new LinkedList();
        }

        for (String s : edgeList) {
            int[] parts = Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray();
            adjList[parts[0]].add(parts[1]); // This makes me sad :(
        }

        for (int i = 0; i < adjList.length; i++) {
            LinkedList list = adjList[i];
            System.out.println(i + ": " + ((Stream<Object>) list.stream()).map(Object::toString).collect(Collectors.joining(" -> ")));
        }


    }

}
