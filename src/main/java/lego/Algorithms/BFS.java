package lego.Algorithms;

import lego.Graph;

import java.util.*;

public class BFS {
    private Graph graph = new Graph();

    BFS(Graph graph) {
        this.graph = graph;
    }

    /**
     * Calculate length of shortest path between vertices using breadth-first search algorithm.
     * Works with OUTGOING edges
     *
     * @param start  start vertex
     * @param target target vertex
     * @return length of shortest path between vertices
     */
    public long getPathLength(long start, long target) {
        if (start == target) {
            return 0L;
        }

        Queue<Long> queue = new LinkedList<>();
        List<Long> visited = new ArrayList<>();
        Map<Long, Long> destinations = new HashMap<>();
        queue.add(start);
        destinations.put(start, 0L);

        while (!queue.isEmpty()) {
            long v = queue.poll();

            for (long vertex : graph.getAdjacentVertices(v)) {
                if (vertex == target) {
                    return destinations.getOrDefault(v, 0L) + 1;
                }

                if (!visited.contains(vertex)) {
                    visited.add(v);
                    queue.add(vertex);
                    destinations.put(vertex, destinations.get(v) + 1);
                }
            }
        }

        return 0L;
    }
}