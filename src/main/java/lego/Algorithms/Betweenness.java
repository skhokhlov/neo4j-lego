package lego.Algorithms;

import lego.Graph;
import lego.Results.ClosenessResult;

import java.util.HashSet;
import java.util.Iterator;
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

                Stream<HashSet<Long>> paths = bfs.getAllShortestPaths(s, t);
                allPaths += paths.count();

                // Find paths that contains target vertex
                pathsContainsVertex += paths.mapToLong(item -> item.contains(vertexId) ? 1 : 0).sum();
            }

        }

        return (double) pathsContainsVertex / (double) allPaths;
    }

    /**
     * Calculate betweenness centrality for each vertex in graph
     * and return stream of {@link ClosenessResult} class.
     *
     * @param graph graph for calculations
     * @return Stream of {@link ClosenessResult} with scores
     */
    public Stream<ClosenessResult> getScores(Graph graph) {
        return graph.getVertexStream().map(vertex -> new ClosenessResult(vertex, getVertexScore(graph, vertex)));
    }
}
