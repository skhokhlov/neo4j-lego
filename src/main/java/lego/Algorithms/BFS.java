package lego.Algorithms;

import lego.Graph;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
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
     * @param start  This is start vertex
     * @param target This is target vertex
     * @return Length of shortest path between vertices
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

    /**
     * Find all shortest paths between two vertices in graph.
     *
     * @param start  This is start vertex
     * @param target This is target vertex
     * @return Stream of List with shortest paths
     */
    public Stream<List<Long>> findAllShortestPaths(long start, long target) {
        if (start == target) {
            return Stream.empty();
        }

        Queue<Long> queue = new LinkedList<>();
        Set<Long> visited = new HashSet<>();
        ArrayList<HashSet<Long>> prev = new ArrayList<HashSet<Long>>();

        for (int i = 0; i <= graph.size(); i++) {
            prev.add(new HashSet<>());
        }
        queue.add(start);
        long vertexTo = -1;

        while (!queue.isEmpty()) {
            long next = queue.poll();

            if (next == target) {
                vertexTo = next;
                break;
            }

            for (long n : graph.getAdjacentVertices(next)) {
                if (!visited.contains(n)) {
                    if (!queue.contains(n)) {
                        queue.add(n);
                    }

                    prev.get((int) n).add(next);
                }
            }

            queue.remove(next);
            visited.add(next);
        }

        if (vertexTo == -1) {
//            return Collections.emptyList();
            return Stream.empty();
        }

        List<List<Long>> result = new ArrayList<>();

        dfs(vertexTo, result, prev, new LinkedList<>());

        // NavigableMap<Long, List<List<Long>>>
        return result.parallelStream()
                .collect(Collectors.groupingBy(List::size, TreeMap::new, Collectors.toList()))
                .firstEntry().getValue().stream();
    }

    private void dfs(long n, List<List<Long>> result, ArrayList<HashSet<Long>> prev, LinkedList<Long> path) {
        path.addFirst(n);
        if (prev.get((int) n).size() == 0) {
            result.add(new ArrayList<>(path));
        }

        for (long p : prev.get((int) n)) {
            dfs(p, result, prev, path);
        }

        path.removeFirst();
    }

}
