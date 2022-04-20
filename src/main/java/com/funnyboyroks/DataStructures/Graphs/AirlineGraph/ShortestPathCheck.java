package com.funnyboyroks.DataStructures.Graphs.AirlineGraph;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ShortestPathCheck implements AirlineGraph {
    private static int[][] graph;
    int V;

    public ShortestPathCheck() throws IOException {
        this.V = airportCode.length;
        int size = airportCode.length;
        graph = new int[size][size];

        int r;
        int c;
        int w;
        for(Scanner sc = new Scanner(new File("connections.dat")); sc.hasNextLine(); graph[r][c] = w) {
            String line = sc.nextLine();
            String[] buffer = line.split(",");
            r = Integer.parseInt(buffer[0]);
            c = Integer.parseInt(buffer[1]);
            w = Integer.parseInt(buffer[2]);
        }

    }

    public int findAirportCode(String airportCode) {
        for(int x = 0; x < ShortestPathCheck.airportCode.length; ++x) {
            if (airportCode.equals(ShortestPathCheck.airportCode[x])) {
                return x;
            }
        }

        return -1;
    }

    int minDistance(int[] dist, Boolean[] sptSet) {
        int min = Integer.MAX_VALUE;
        int min_index = -1;

        for(int v = 0; v < this.V; ++v) {
            if (!sptSet[v] && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        }

        return min_index;
    }

    int[] dijkstra(String source) {
        int src = this.findAirportCode(source);
        int[] dist = new int[this.V];
        Boolean[] sptSet = new Boolean[this.V];

        int count;
        for(count = 0; count < this.V; ++count) {
            dist[count] = Integer.MAX_VALUE;
            sptSet[count] = false;
        }

        dist[src] = 0;

        for(count = 0; count < this.V - 1; ++count) {
            int u = this.minDistance(dist, sptSet);
            sptSet[u] = true;

            for(int v = 0; v < this.V; ++v) {
                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        return dist;
    }

    public String findRoute(int length, String start, String end) {
        return "";
    }
}