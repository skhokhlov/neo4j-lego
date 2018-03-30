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
    /**
     * List with edges of the graph
     */
    private Collection<Edge> edges = new ArrayList<>();

    /**
     * Graph constructor
     */
    public Graph() {
        this.edges = new ArrayList<>();
    }

    /**
     * Copy constructor for graph
     *
     * @param graph Graph to be copied
     */
    public Graph(Graph graph) {
        this.edges = graph.edges;
    }

    /**
     * Check that graph do not contains edges
     *
     * @return True if empty and False if not
     */
    public boolean isEmpty() {
        return edges.isEmpty();
    }

    /**
     * Size of the graph
     *
     * @return Count of edges in the graph
     */
    public int size() {
        return edges.size();
    }

    /**
     * Make Stream of {@link Edge}
     *
     * @return Stream of {@link Edge}
     */
    public Stream<Edge> getStream() {
        return edges.stream();
    }

    /**
     * Make parallel stream of {@link Edge}
     *
     * @return Parallel stream of {@link Edge}
     */
    public Stream<Edge> getParallelStream() {
        return edges.parallelStream();
    }

    /**
     * Make Stream of vertex ids
     *
     * @return Stream of Integer
     */
    public Stream<Integer> getVertexStream() {
        Collection<Integer> vertices = new ArrayList<>();

        for (Edge edge : edges) {
            if (!vertices.contains(edge.getStart())) {
                vertices.add(edge.getStart());
            }
            if (!vertices.contains(edge.getEnd())) {
                vertices.add(edge.getEnd());
            }
        }

        return vertices.stream();
    }

    /**
     * Parallel stream of getVertexStream()
     *
     * @return Parallel stream of vertex ids
     */
    public Stream<Integer> getParallelVertexStream() {
        return this.getVertexStream().parallel();
    }

    /**
     * Make list of incident edges for vertex
     *
     * @param vertexId This is vertex id
     * @return List with edges
     */
    public List<Edge> getIncidentEdges(int vertexId) {
        return this.getStream().filter(edge -> edge.containsVertex(vertexId)).collect(Collectors.toList());
    }

    /**
     * Make list of outgoing edges for vertex
     *
     * @param vertexId This is vertex id
     * @return List with edges
     */
    public List<Edge> getOutgoingEdges(int vertexId) {
        return this.getStream().filter(edge -> edge.getStart() == vertexId).collect(Collectors.toList());
    }

    /**
     * Make list of adjacent vertices by outgoing edges
     *
     * @param vertexId This is vertex id
     * @return List with adjacent vertices
     */
    public List<Integer> getAdjacentVertices(int vertexId) {
        return this.getStream().filter(edge -> edge.getStart() == vertexId).map(Edge::getEnd).collect(Collectors.toList());
    }

    /**
     * Count of vertices in the graph
     *
     * @return Count of vertices
     */
    public int getVerticesCount() {
        return (int) this.getVertexStream().count();
    }

    /**
     * Add new edge to graph and return them
     *
     * @param edge This is new {@link Edge}
     * @return Current {@link Graph}
     */
    public Graph addEdge(Edge edge) {
        edges.add(edge);
        return this;
    }

    /**
     * Remove edge from graph and return them
     *
     * @param edge This is vertex to be removed
     * @return Current {@link Graph}
     */
    public Graph removeEdge(Edge edge) {
        edges.remove(edge);
        return this;
    }

    /**
     * Check that graph contains edge
     *
     * @param edge This is edge to be checked
     * @return True if contains and False if not
     */
    public boolean containsEdge(Edge edge) {
        return this.getStream().anyMatch(edge::equals);
    }

    /**
     * Check that graph contains vertex
     *
     * @param id This is vertex id
     * @return True if contains and False if not
     */
    public boolean containsVertex(int id) {
        return this.getStream().anyMatch(edge -> edge.containsVertex(id));
    }

}