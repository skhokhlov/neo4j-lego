package lego.Algorithms;

import lego.Graph;
import lego.Results.CentralityResult;

import java.util.*;
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
        return graph.getVertexStream().map(vertex -> new CentralityResult(vertex, getVertexScore(graph, vertex)));
    }
}
