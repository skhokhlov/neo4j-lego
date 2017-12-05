package lego;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class of graph.
 * Based on storage of edges via ArrayList with {@link Edge}.
 */
public class Graph {
    //    List<Long> edges = new ArrayList<>(); // Array with pairs of edges
    private Collection<Edge> edges = new ArrayList<>();

    public Graph() {
        this.edges = new ArrayList<>();
    }

    public Graph(Graph graph) {
        this.edges = graph.edges;
    }

    public boolean isEmpty() {
        return edges.isEmpty();
    }

    public long size() {
        return edges.size();
    }

    /**
     * @return stream of {@link Edge}
     */
    public Stream<Edge> getStream() {
        return edges.stream();
    }

    /**
     * @return parallel stream of {@link Edge}
     */
    public Stream<Edge> getParallelStream() {
        return edges.parallelStream();
    }

    /**
     * Make Stream of vertex ids
     * TODO: Check that it works correctly. ArrayList is not synchronized. Maybe need to use not parallel stream
     *
     * @return Stream of Long
     */
    public Stream<Long> getVertexStream() {
        Collection<Long> vertices = new ArrayList<>();
        this.getParallelStream().forEach(edge -> {
            if (!vertices.contains(edge.getStart())) {
                vertices.add(edge.getStart());
            }
            if (!vertices.contains(edge.getEnd())) {
                vertices.add(edge.getEnd());
            }
        });

        return vertices.stream();
    }

    /**
     * Parallel stream of getVertexStream()
     *
     * @return parallel stream of vertex ids
     */
    public Stream<Long> getParallelVertexStream() {
        return this.getVertexStream().parallel();
    }

    /**
     * Make list of incident edges for vertex
     *
     * @param vertexId vertex id
     * @return list with edges
     */
    public List<Edge> getIncidentEdges(long vertexId) {
        return this.getStream().filter(edge -> edge.containsVertex(vertexId)).collect(Collectors.toList());
    }

    /**
     * Make list of outgoing edges for vertex
     *
     * @param vertexId vertex id
     * @return list with edges
     */
    public List<Edge> getOutgoingEdges(long vertexId) {
        return this.getStream().filter(edge -> edge.getStart() == vertexId).collect(Collectors.toList());
    }

    /**
     * Make list of adjacent vertices by outgoing edges
     *
     * @param vertexId vertex id
     * @return list with adjacent vertices
     */
    public List<Long> getAdjacentVertices(long vertexId) {
        return this.getStream().filter(edge -> edge.getStart() == vertexId).map(Edge::getEnd).collect(Collectors.toList());
    }

    /**
     * Add new edge to graph and return them
     *
     * @param edge added vertex
     * @return this graph
     */
    public Graph addEdge(Edge edge) {
        edges.add(edge);
        return this;
    }

    /**
     * Remove edge from graph and return them
     *
     * @param edge removed vertex
     * @return this graph
     */
    public Graph removeEdge(Edge edge) {
        edges.remove(edge);
        return this;
    }

    /**
     * Check that graph contains edge
     *
     * @param edge checked edge
     * @return boolean
     */
    public boolean containsEdge(Edge edge) {
        return this.getStream().anyMatch(edge::equals);
    }

    /**
     * Check that graph contains vertex
     *
     * @param id vertex id
     * @return boolean
     */
    public boolean containsVertex(long id) {
        return this.getStream().anyMatch(edge -> edge.containsVertex(id));
    }

}