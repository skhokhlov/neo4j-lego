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

    /**
     * Find all shortest paths between two vertices in graph.
     *
     * @param start start vertex
     * @param target target vertex
     * @return stream of shortest paths
     */
//    public Stream<HashSet<Long>> getAllShortestPaths(long start, long target) {
//        Queue<Long> queue = new LinkedList<>();
//        Map<Long, Long> destinations = new HashMap<>();
//        HashSet<Long>[] prev = new HashSet[graph.size()]; // need array, int too small
//        queue.add(start);
//        destinations.put(start, 0L);
//
//        while (!queue.isEmpty()) {
//            long v = queue.poll();
//
//            for (long vertex : graph.getAdjacentVertices(v)) {
//                if (!destinations.containsKey(vertex)) {
//                    queue.add(vertex);
//                    destinations.put(vertex, destinations.get(v) + 1);
//                    prev
//                    prev.get(v).add(vertex);
//                } else if (destinations.get(vertex) == (destinations.get(v) + 1)) {
//                    prev.get(v).add(vertex);
//                }
//            }
//        }
//
//        Collection<HashSet<Long>> allPaths = new ArrayList<>();
//        Vector<Long> currentPath = new Vector<>();
//        Stack<Long> stack = new Stack<>();
//
////        currentPath.add(target);
////        if (prev.get(target).size() == 0) {
////            allPaths.add(new HashSet<Long>(currentPath.begin(), currentPath.end()));
////        }
////
////        stack.push(target);
////        while (!stack.empty()) {
////
////        }
//        dfs(prev, target, allPaths, currentPath);
//        return allPaths.stream();
//    }

    private void dfs(Map<Long, HashSet<Long>> prev, long node, Collection<HashSet<Long>> allPaths, Vector<Long> currentPath) {
        currentPath.add(node);
        if (prev.get(node).size() == 0) {
            allPaths.add(new HashSet<>(Arrays.asList(currentPath.firstElement(), currentPath.lastElement())));
        }

        for (long vertex : prev.get(node)) {
            dfs(prev, vertex, allPaths, currentPath);
        }

        currentPath.remove(currentPath.size()-1);
    }
}
