package lego.Algorithms;

import lego.Direction;
import lego.Graph;
import lego.Results.ClosenessResult;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        throw new NotImplementedException();
//        this.direction = direction;
//        return this;
    }

    /**
     * Calculate closeness centrality for specific vertex in graph
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

        long sum = graph.getParallelVertexStream().mapToLong(vertex -> bfs.getPathLength(vertexId, vertex)).sum();

        return 1 / (double) sum;
    }

    /**
     * Calculate closeness centrality for each vertex in graph
     * and return stream of lego.Results.ClosenessResult class.
     *
     * @param graph graph for calculations
     * @return Stream\<ClosenessResult\> with scores
     */
    public Stream<ClosenessResult> getScores(Graph graph) {
        return graph.getVertexStream().map(vertex -> new ClosenessResult(vertex, getVertexScore(graph, vertex)));
    }
}
