package com.funnyboyroks.DataStructures.Graphs.AirlineGraph;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

public class Graph implements AirlineGraph {

    private final int[][]        graph;
    private       Stack<Integer> stack;

    public Graph() {
        this.graph = parse(10, "connections.dat");
    }

    public int[] shortestPath(String src) {
        var dist = new int[AirlineGraph.airportCode.length];
        var barr = new boolean[AirlineGraph.airportCode.length];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(barr, false);

        dist[this.findAirportCode(src)] = 0;

        for (int i = 0; i < AirlineGraph.airportCode.length - 1; ++i) {
            int min = Integer.MAX_VALUE;
            int minI = -1;

            for (int j = 0; j < AirlineGraph.airportCode.length; ++j) {
                if (!barr[j] && dist[j] <= min) {
                    min = dist[j];
                    minI = j;
                }
            }

            barr[minI] = true;

            for (int j = 0; j < AirlineGraph.airportCode.length; ++j) {
                if (
                    !barr[j] && this.graph[minI][j] != 0
                    && dist[minI] != Integer.MAX_VALUE
                    && dist[minI] + this.graph[minI][j] < dist[j]
                ) {
                    dist[j] = dist[minI] + this.graph[minI][j];
                }
            }
        }

        return dist;
    }

    /**
     * This is a really bad impl, but idrc :P
     */
    public String cheapestRoute(String from, String to) {
        int minCost = Integer.MAX_VALUE;
        Stack<Integer> cheapestRoute = null;
        for (int i = 0; i < AirlineGraph.airportCode.length; i++) {
            this.stack = new Stack<>();
            try {
                findPath(i, new Point(findAirportCode(from), findAirportCode(to)));
            } catch (StackOverflowError e) { // hehe lmao -- e fish in sea
                continue;
            }
            int cost = 0;
            if (!this.stack.isEmpty()) {
                Collections.reverse(this.stack);
                this.stack.add(findAirportCode(to));
                for (int j = 0; j < this.stack.size() - 1; j++) {
                    cost += this.graph[this.stack.get(j)][this.stack.get(j + 1)];

                }

                if (cost < minCost) {
                    minCost = cost;
                    cheapestRoute = this.stack;
                }
            }
        }
        if (cheapestRoute == null) {
            return "There is no such connection!";
        }
        return cheapestRoute.stream().map(i -> padStart(AirlineGraph.airportCode[i], 3)).collect(Collectors.joining(" -> ")) + " (" + minCost + ")";

    }

    @Override
    public String findRoute(int length, String start, String end) {
        this.stack = new Stack<>();
        var edge = getEdge(start, end);
        findPath(length, edge);
        if (this.stack.isEmpty()) {
            return "There is no such connection!";
        }
        Collections.reverse(this.stack);
        this.stack.add(edge.y);
        int cost = 0;

        for (int j = 0; j < this.stack.size() - 1; j++) {
            cost += this.graph[this.stack.get(j)][this.stack.get(j + 1)];
        }

        return this.stack.stream().map(i -> padStart(AirlineGraph.airportCode[i], 3)).collect(Collectors.joining(" -> ")) + " (" + cost + ")";
    }

    private Point getEdge(String start, String end) {
        return new Point(findAirportCode(start), findAirportCode(end));
    }

    private int findAirportCode(String airportCode) {

        for (int i = 0; i < AirlineGraph.airportCode.length; i++) {
            if (AirlineGraph.airportCode[i].equals(airportCode)) {
                return i;
            }
        }
        return -1;
    }

    private boolean adjacent(Point edge) {
        return this.graph[edge.x][edge.y] > 0;
    }

    private boolean findPath(int length, Point p) {
        if (length == 1) {
            if (adjacent(p)) {
                this.stack.add(p.x);
                return true;
            }
        } else {
            for (int i = 0; i < this.graph.length; i++) {
                if (this.graph[p.x][i] > 0 && findPath(length - 1, new Point(i, p.y))) {
                    this.stack.add(p.x);
                    return true;
                }
            }
        }
        return false;
    }

    private static String padStart(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
            .append("    ")
            .append(Arrays.stream(AirlineGraph.airportCode)
                        .map(n -> padStart(n, 4))
                        .collect(Collectors.joining(" ")))
            .append("\n");
        for (int i = 0; i < this.graph.length; i++) {
            int[] ints = this.graph[i];
            sb
                .append(AirlineGraph.airportCode[i]).append(" ")
                .append(Arrays.stream(ints)
                            .mapToObj(n -> padStart(n == 0 ? "-" : n + "", 4))
                            .collect(Collectors.joining(" ")))
                .append("\n");
        }
        return sb.toString();
    }

    private static int[][] parse(int size, String file) {
        var arr = new int[size][size];
        try {
            Scanner scanner = new Scanner(new File(file));
            while (scanner.hasNextLine()) {
                var parts = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
                arr[parts[0]][parts[1]] = parts[2];

            }
        } catch (Exception e) { // I don't want to deal with it properly :P
            throw new RuntimeException(e);
        }
        return arr;
    }
}
