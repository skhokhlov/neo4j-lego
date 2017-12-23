package lego.Algorithms;

import lego.Graph;
import lego.Results.CentralityResult;

import java.util.Iterator;
import java.util.List;
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
    public double getVertexScore(Graph graph, long vertexId) {
        if (!graph.containsVertex(vertexId)) {
            throw new IllegalArgumentException("Graph do not contains this vertex");
        }

        BFS bfs = new BFS(graph);

        long allPaths = 0;
        long pathsContainsVertex = 0;

        for (Iterator<Long> it = graph.getVertexStream().iterator(); it.hasNext(); ) {
            long s = it.next();

            for (Iterator<Long> it2 = graph.getVertexStream().iterator(); it2.hasNext(); ) {
                long t = it2.next();

                if (s == t) {
                    continue;
                }

                Supplier<Stream<List<Long>>> paths = () -> bfs.getAllShortestPaths(s, t);
                allPaths += paths.get().count();

                // Find paths that contains target vertex
                pathsContainsVertex += paths.get().mapToLong(item -> item.contains(vertexId) ? 1 : 0).sum();
            }

        }

        return (double) pathsContainsVertex / (double) allPaths;
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
