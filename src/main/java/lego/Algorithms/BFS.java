package lego.Algorithms;

import lego.Graph;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

public class BFS {
    private Graph graph;

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


    private static Object mock = new Object();
    /**
     * Find all shortest paths between two vertices in graph.
     *
     * @param from  start vertex
     * @param to target vertex
     * @return stream of shortest paths
     */
    public Stream<List<Long>> getAllShortestPaths(long from, long to) {
        LinkedHashMap<Long, Object> queue = new LinkedHashMap<>();
        Set<Long> visited = new HashSet<>();
        ArrayList<HashSet<Long>> prev = new ArrayList<>();
        queue.put(from, mock);
        for (int i = 0; i <= graph.size(); i++) {
            prev.add(new HashSet<>());
        }

        long nodeTo = -1;
        while (queue.keySet().size() > 0) {
            long next = queue.keySet().iterator().next();

            if (next == to) {
                // base case: we found the end node and processed all edges to it -> we are done
                nodeTo = next;
                break;
            }

            for (long n : graph.getAdjacentVertices(next)) {
                if (!visited.contains(n)) {
                    queue.putIfAbsent(n, mock);
                    prev.get((int) n).add(next);
                }
            }

            // removing the node from queue
            queue.remove(next);
            visited.add(next);
        }
        if (nodeTo == -1) {
            return Stream.empty();
        }

        // Now performing the dfs from target node to gather all paths
        List<List<Long>> result = new ArrayList<>();
        dfs(nodeTo, result, new LinkedList<>(), prev);

        return result.stream();
    }

    private void dfs(long n, List<List<Long>> result, LinkedList<Long> path, List<HashSet<Long>> prev) {
        path.addFirst(n);
        if (prev.get((int) n).size() == 0) {
            // base case: we came to target vertex
            result.add(new ArrayList<>(path));
        }
        for (long vertex : prev.get((int) n)) {
            dfs(vertex, result, path, prev);
        }
        // do not forget to remove the processed element from path
        path.removeFirst();
    }

}
