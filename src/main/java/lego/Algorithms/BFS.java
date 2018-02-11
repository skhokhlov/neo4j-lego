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

    /**
     * Find all shortest paths between two vertices in graph.
     *
     * @param start  This is start vertex
     * @param target This is target vertex
     * @return Stream of List with shortest paths
     */
    public Stream<HashSet<Long>> getAllShortestPaths(long start, long target) {
        if (start == target) {
            return Stream.empty();
        }

        Queue<Long> queue = new LinkedList<>();
        Map<Long, Long> destinations = new HashMap<>();
        ArrayList<HashSet<Long>> prev = new ArrayList<HashSet<Long>>();
        queue.add(start);
        destinations.put(start, 0L);
        for (int i = 0; i <= graph.size(); i++) {
            prev.add(new HashSet<>());
        }

        while (!queue.isEmpty()) {
            long v = queue.poll();

            for (long vertex : graph.getAdjacentVertices(v)) {
                if (!destinations.containsKey(vertex)) {
                    // child node not visited yet
                    queue.add(vertex);
                    destinations.put(vertex, destinations.get(v) + 1);
                } else if (destinations.get(vertex) == destinations.get(v) + 1) {
                    // multiple child nodes with save distance
                    prev.get((int) vertex).add(v);
                }
            }
        }

        Collection<HashSet<Long>> allPaths = new ArrayList<>();
        Vector<Long> currentPath = new Vector<>();
//        Stack<Long> stack = new Stack<>();

//        currentPath.add(target);
//        if (prev.get(target).size() == 0) {
//            allPaths.add(new HashSet<Long>(currentPath.begin(), currentPath.end()));
//        }
//
//        stack.push(target);
//        while (!stack.empty()) {
//
//        }
        dfs(prev, target, allPaths, currentPath);
        return allPaths.stream();
    }
//    public Stream<List<Long>> getAllShortestPaths(long start, long target) {
//        if (start == target) {
//            return Stream.empty();
//        }
//
//        LinkedHashMap<Long, Object> queue = new LinkedHashMap<>();
//        Set<Long> visited = new HashSet<>();
//        ArrayList<HashSet<Long>> prev = new ArrayList<>();
//        queue.put(start, mock);
//        for (int i = 0; i <= graph.size(); i++) {
//            prev.add(new HashSet<>());
//        }
//
//        long nodeTo = -1;
//        while (queue.keySet().size() > 0) {
//            long next = queue.keySet().iterator().next();
//
//            if (next == target) {
//                // base case: we found the end node and processed all edges to it -> we are done
//                nodeTo = next;
//                break;
//            }
//
//            for (long n : graph.getAdjacentVertices(next)) {
//                if (!visited.contains(n)) {
//                    queue.putIfAbsent(n, mock);
//                    prev.get((int) n).add(next);
//                }
//            }
//
//            // removing the node start queue
//            queue.remove(next);
//            visited.add(next);
//        }
//        if (nodeTo == -1) {
//            return Stream.empty();
//        }
//
//        // Now performing the dfs from target node to gather all paths
//        List<List<Long>> result = new ArrayList<>();
//        dfs(nodeTo, result, new LinkedList<>(), prev);
//
//        return result.stream();
//    }

//    private void dfs(long n, List<List<Long>> result, LinkedList<Long> path, List<HashSet<Long>> prev) {
//        path.addFirst(n);
//        if (prev.get((int) n).size() == 0) {
//            // base case: we came to target vertex
//            result.add(new ArrayList<>(path));
//        }
//        for (long vertex : prev.get((int) n)) {
//            dfs(vertex, result, path, prev);
//        }
//        // do not forget to remove the processed element from path
//        path.removeFirst();
//    }

    private void dfs(ArrayList<HashSet<Long>> prev, long node, Collection<HashSet<Long>> allPaths, Vector<Long> currentPath) {
        currentPath.add(node);
        if (prev.get((int) node).size() == 0) {
            allPaths.add(new HashSet<>(Arrays.asList(currentPath.firstElement(), currentPath.lastElement())));
        }

        for (long vertex : prev.get((int) node)) {
            dfs(prev, vertex, allPaths, currentPath);
        }

        currentPath.remove(currentPath.size() - 1);
    }

}
