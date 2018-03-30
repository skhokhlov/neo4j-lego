package lego.Algorithms;

import lego.Direction;
import lego.Graph;
import lego.Results.DegreeResult;

import java.util.stream.Stream;

/**
 * Class for calculating degree of vertices with specific direction (both, incoming, outgoing).
 * Can calculate degree for one vertex and for all vertices in graph.
 */
public class Degree {
    private Direction direction = Direction.OUTGOING;

    public Direction getDirection() {
        return direction;
    }

    /**
     * Set direction param
     *
     * @param direction edge directions for degree calculation
     * @return Direction
     */
    public Degree setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }


    /**
     * Calculate degree for specific vertex in graph with specific direction
     *
     * @param graph    graph for calculations
     * @param vertexId id of vertex
     * @return degree of vertex
     */
    public int getVertexScore(Graph graph, int vertexId) {
        if (!graph.containsVertex(vertexId)) {
            throw new IllegalArgumentException("Graph do not contains this vertex");
        }

        if (Direction.BOTH == direction) {
            return (int) graph.getStream().filter(edge -> edge.containsVertex(vertexId)).count(); // Vertex degree for undirected graph
        } else if (Direction.OUTGOING == direction) {
            return (int) graph.getStream().filter(edge -> edge.getStart() == vertexId).count();
        } else {
            return (int) graph.getStream().filter(edge -> edge.getEnd() == vertexId).count();
        }
    }

    /**
     * Calculate degree for each vertex in graph with specific direction
     * and return stream of {@link DegreeResult} class.
     * Calculating with concurrency via parallel stream.
     *
     * @param graph graph for calculations
     * @return Stream of {@link DegreeResult} with scores
     */
    public Stream<DegreeResult> getScores(Graph graph) {
        return graph.getParallelVertexStream().map(vertex -> new DegreeResult(vertex, getVertexScore(graph, vertex))); // It seems not efficient
    }
}
