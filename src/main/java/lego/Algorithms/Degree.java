package lego.Algorithms;

import lego.Direction;
import lego.Graph;
import lego.Results.DegreeResult;

import java.util.stream.Stream;

public class Degree {
    private Direction direction = Direction.OUTGOING;

    public Direction getDirection() {
        return direction;
    }

    /**
     * Set direction param
     *
     * @param direction
     * @return
     */
    public Degree withDirection(Direction direction) {
        this.direction = direction;
        return this;
    }


    /**
     * Calculate degree for specific vertex in graph with specific direction
     *
     * @param graph
     * @param vertexId
     * @return
     */
    public long getVertexScore(Graph graph, long vertexId) {
        if (!graph.containsVertex(vertexId)) {
            throw new IllegalArgumentException("Graph do not contains this vertex");
        }

        if (Direction.BOTH == direction) {
            return graph.getStream().filter(edge -> edge.containsVertex(vertexId)).count(); // Vertex degree for undirected graph
        } else if (Direction.OUTGOING == direction) {
            return graph.getStream().filter(edge -> edge.getStart() == vertexId).count();
        } else {
            return graph.getStream().filter(edge -> edge.getEnd() == vertexId).count();
        }
    }

    /**
     * Calculate degree for each vertex in graph with specific direction and return stream of lego.Results.DegreeResult class
     *
     * @param graph
     * @return
     */
    public Stream<DegreeResult> getScores(Graph graph) {
        return graph.getParallelVertexStream().map(vertex -> new DegreeResult(vertex, getVertexScore(graph, vertex))); // It seems not efficient
    }
}
