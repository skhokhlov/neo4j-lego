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
    public int getPathLength(int start, int target) {
        if (start == target) {
            return 0;
        }

        Queue<Integer> queue = new LinkedList<>();
        List<Integer> visited = new ArrayList<>();
        Map<Integer, Integer> destinations = new HashMap<>();
        queue.add(start);
        destinations.put(start, 0);

        while (!queue.isEmpty()) {
            int v = queue.poll();

            for (int vertex : graph.getAdjacentVertices(v)) {
                if (vertex == target) {
                    return destinations.getOrDefault(v, 0) + 1;
                }

                if (!visited.contains(vertex)) {
                    visited.add(v);
                    queue.add(vertex);
                    destinations.put(vertex, destinations.get(v) + 1);
                }
            }
        }

        return 0;
    }

    /**
     * Find all shortest paths between two vertices in graph.
     *
     * @param start  This is start vertex
     * @param target This is target vertex
     * @return Stream of List with shortest paths
     */
    public Stream<List<Integer>> findAllShortestPaths(int start, int target) {
        if (start == target) {
            return Stream.empty();
        }

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        ArrayList<HashSet<Integer>> prev = new ArrayList<HashSet<Integer>>();

        for (int i = 0; i <= graph.size(); i++) {
            prev.add(new HashSet<>());
        }
        queue.add(start);
        int vertexTo = -1;

        while (!queue.isEmpty()) {
            int next = queue.poll();

            if (next == target) {
                vertexTo = next;
                break;
            }

            for (int n : graph.getAdjacentVertices(next)) {
                if (!visited.contains(n)) {
                    if (!queue.contains(n)) {
                        queue.add(n);
                    }

                    prev.get(n).add(next);
                }
            }

            queue.remove(next);
            visited.add(next);
        }

        if (vertexTo == -1) {
//            return Collections.emptyList();
            return Stream.empty();
        }

        List<List<Integer>> result = new ArrayList<>();

        dfs(vertexTo, result, prev, new LinkedList<>());

        // NavigableMap<Long, List<List<Long>>>
        return result.parallelStream()
                .collect(Collectors.groupingBy(List::size, TreeMap::new, Collectors.toList()))
                .firstEntry().getValue().stream();
    }

    private void dfs(int n, List<List<Integer>> result, ArrayList<HashSet<Integer>> prev, LinkedList<Integer> path) {
        path.addFirst(n);
        if (prev.get(n).size() == 0) {
            result.add(new ArrayList<>(path));
        }

        for (int p : prev.get(n)) {
            dfs(p, result, prev, path);
        }

        path.removeFirst();
    }

}
