package lego.Algorithms;

import lego.Graph;
import lego.Results.CentralityResult;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Betweenness {
    /**
     * Calculate betweenness centrality for specific vertex in graph
     *
     * @param graph    graph for calculations
     * @param vertexId id of vertex
     * @return vertex score
     */
    public double getVertexScore(Graph graph, int vertexId) {
        if (!graph.containsVertex(vertexId)) {
            throw new IllegalArgumentException("Graph do not contains this vertex");
        }

        BFS bfs = new BFS(graph);

        double betweenness = 0;

        for (Iterator<Integer> it = graph.getVertexStream().iterator(); it.hasNext(); ) {
            int s = it.next();

            for (Iterator<Integer> it2 = graph.getVertexStream().iterator(); it2.hasNext(); ) {
                int t = it2.next();

                if (s == t) {
                    continue;
                }

                Supplier<Stream<List<Integer>>> paths = () -> bfs.findAllShortestPaths(s, t);
//                Supplier<Stream<HashSet<Long>>> paths = () -> bfs.getAllShortestPaths(s, t);
                long allPaths = paths.get().count();

                if (allPaths != 0) {
                    // Find paths that contains target vertex
                    long pathsContainsVertex = paths.get().mapToLong(item -> {
                        for (int i : item.subList(1, item.size() - 1)) { // check all elements except first and last
                            if (i == vertexId) {
                                return 1;
                            }
                        }

                        return 0;
                    }).sum();

                    betweenness += (double) pathsContainsVertex / (double) allPaths;
                }
            }

        }

        return betweenness;
    }

    /**
     * Calculate betweenness centrality for each vertex in graph
     * and return stream of {@link CentralityResult} class.
     *
     * @param graph graph for calculations
     * @return Stream of {@link CentralityResult} with scores
     */
    public Stream<CentralityResult> getScores(Graph graph) {
//        return graph.getVertexStream().map(vertex -> new CentralityResult(vertex, getVertexScore(graph, vertex)));
        return calc(graph);
    }

    public Stream<CentralityResult> getScoresOld(Graph graph) {
        return graph.getVertexStream().map(vertex -> new CentralityResult(vertex, getVertexScore(graph, vertex)));
    }

//    public double getVertexScoreParallel(Graph graph, int vertexId) {
//        int cores = Runtime.getRuntime().availableProcessors();
//        ExecutorService executorService = Executors.newFixedThreadPool(cores);
//        List<FutureTask<Integer>> taskList = new AbstractList<FutureTask<Integer>>();
//    }

    private Stream<CentralityResult> calc(Graph graph) {


//        PrimitiveIntObjectMap predecessors = Primitive.intObjectMap(); // It's like prev array
//        Map<Integer, Double> map = new HashMap<>();
        ConcurrentHashMap<Integer, Double> map1 = new ConcurrentHashMap<>();


//        int processedNode = 0;
//        for (int source = start; source < end; source++) { // compute for each node
//            processedNode++;
        graph.getParallelVertexStream().forEach((source) -> {
//            if (new Degree().getVertexScore(graph, source) == 0) {
////            if (sourceDegreeData[source] == 0) { // node degree
//                continue;
//            }
            Stack<Integer> stack = new Stack<>(); // S
            Queue<Integer> queue = new LinkedList<>();

//            int numShortestPaths[] = new int[graph.getVerticesCount()]; // sigma
            Map<Integer, Integer> numShortestPaths = new HashMap<>();
//            int distance[] = new int[graph.getVerticesCount()]; // distance
            Map<Integer, Integer> distance = new HashMap<>();
//            double delta[] = new double[graph.getVerticesCount()];
            Map<Integer, Double> delta = new HashMap<>();

            Map<Integer, List<Integer>> prev = new HashMap<>();

            stack.clear();
//            Arrays.fill(numShortestPaths, 0);
//            numShortestPaths[source] = 1;
            numShortestPaths.put(source, 1);
//            Arrays.fill(distance, -1);
//            distance[source] = 0;
            distance.put(source, 0);
            queue.clear();
            queue.add(source);
//            Arrays.fill(delta, 0);

            while (!queue.isEmpty()) { // algorithm stats here
                int nodeDequeued = queue.remove();
                stack.push(nodeDequeued);

//                int chunkIndex = sourceChunkStartingIndex[nodeDequeued];

                for (long j : graph.getAdjacentVertices(nodeDequeued)) {
//                for (int j = 0; j < degree; j++) {
//                    int target = relationshipTarget[chunkIndex + j];

                    int w = (int) j;
//                    if (distance[w] < 0) { // w = target
                    if (distance.getOrDefault(w, -1) < 0) {
                        queue.add(w);
//                        distance[w] = distance[nodeDequeued] + 1;
                        distance.put(w, distance.getOrDefault(nodeDequeued, -1) + 1);
                    }

                    if (distance.getOrDefault(w, -1) == distance.getOrDefault(nodeDequeued, -1) + 1) {
//                    if (distance[w] == (distance[nodeDequeued] + 1)) {
//                        numShortestPaths[w] = numShortestPaths[w] + numShortestPaths[nodeDequeued];
                        numShortestPaths.put(w, numShortestPaths.getOrDefault(w, 0) + numShortestPaths.get(nodeDequeued));
                        if (!prev.containsKey(w)) {
//                        if (!predecessors.containsKey(target)) {
                            ArrayList<Integer> list = new ArrayList<>();
                            prev.put(w, list);
//                            predecessors.put(target, list);
                        }
                        ((ArrayList<Integer>) prev.get(w)).add(nodeDequeued);
//                        ((ArrayList<Integer>)predecessors.get(target)).add(nodeDequeued);
                    }
                }
            }

            int poppedNode;
            double partialDependency;
            while (!stack.isEmpty()) {
                poppedNode = stack.pop();
                ArrayList<Integer> list = ((ArrayList<Integer>) prev.get(poppedNode));
//                ArrayList<Integer> list = (ArrayList<Integer>)predecessors.get(poppedNode);

                for (int i = 0; list != null && i < list.size(); i++) {
                    int node = list.get(i);
//                    assert (numShortestPaths[poppedNode] != 0);
                    assert (numShortestPaths.get(poppedNode) != 0);
//                    partialDependency = (numShortestPaths[(int) node] / (double) numShortestPaths[poppedNode]);
                    partialDependency = numShortestPaths.get((int) node) / (double) numShortestPaths.get(poppedNode);
//                    partialDependency *= (1.0) + delta[poppedNode];
                    partialDependency *= (1.0) + delta.getOrDefault(poppedNode, 0d);
//                    delta[(int) node] += partialDependency;
                    delta.put(node, delta.getOrDefault(node, 0d) + partialDependency);
                }
                if(poppedNode != source && delta.getOrDefault(poppedNode, 0d) != 0d){
//                if (poppedNode != source && delta[poppedNode] != 0.0) {
//                    if (threadBatchNo == -1) {
//                        betweennessCentrality[poppedNode] = betweennessCentrality[poppedNode] + delta[poppedNode];
//                    } else {
                    Object storedValue = map1.get(poppedNode);
                    if (storedValue != null) {
//                        map1.put(poppedNode, ((double) storedValue) + delta[poppedNode]);
                        map1.put(poppedNode, (double) storedValue + delta.getOrDefault(poppedNode, 0d));
                    } else {
//                        map1.put(poppedNode, delta[poppedNode]);
                        map1.put(poppedNode, delta.getOrDefault(poppedNode, 0d));
                    }
//                    }
                }
            }
        });

        return map1.entrySet().stream().map((entry) -> new CentralityResult(entry.getKey(), entry.getValue()));
    }

//    }
}
