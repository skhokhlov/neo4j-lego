package lego.Algorithms;

import lego.Direction;
import lego.Graph;
import lego.Results.CentralityResult;

import java.util.stream.Stream;

public class Closeness {
    private Direction direction = Direction.OUTGOING;

    public Direction getDirection() {
        return direction;
    }

    /**
     * Set direction param
     * TODO
     *
     * @param direction edge directions for degree calculation
     * @return Direction
     */
    public Closeness setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    /**
     * Calculate closeness centrality for specific vertex in graph using {@link BFS}.
     *
     * @param graph    This is graph for calculations
     * @param vertexId This is id of the vertex
     * @return Centrality score for vertex
     */
    public double getVertexScore(Graph graph, long vertexId) {
        if (!graph.containsVertex(vertexId)) {
            throw new IllegalArgumentException("Graph do not contains this vertex");
        }

        BFS bfs = new BFS(graph);

        long sum = graph.getParallelVertexStream().mapToLong(vertex -> bfs.getPathLength(vertexId, vertex)).sum();

        return 1 / (double) sum;
    }

    /**
     * Calculate closeness centrality for each vertex in graph
     * and return stream of {@link CentralityResult} class.
     *
     * @param graph This is graph for calculations
     * @return Stream of {@link CentralityResult} with scores
     */
    public Stream<CentralityResult> getScores(Graph graph) {
        return graph.getVertexStream().map(vertex -> new CentralityResult(vertex, getVertexScore(graph, vertex)));
    }
}
