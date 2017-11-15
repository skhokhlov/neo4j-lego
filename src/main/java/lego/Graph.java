package lego;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * Interface of graph
 */
public class Graph {
    //    List<Long> edges = new ArrayList<>(); // Array with pairs of edges
    private Collection<Edge> edges = new ArrayList<>();

    public Stream<Edge> getStream() {
        return edges.stream();
    }

    public Stream<Edge> getParallelStream() {
        return edges.parallelStream();
    }

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

    public Stream<Long> getParallelVertexStream() {
        return this.getVertexStream().parallel();
    }

    public Graph addEdge(Edge edge) {
        edges.add(edge);
        return this;
    }

    public Graph removeEdge(Edge edge) {
        edges.remove(edge);
        return this;
    }

    public boolean containsEdge(Edge edge) {
        return this.getStream().anyMatch(edge::equals);
    }

    public boolean containsVertex(long id) {
        return this.getStream().anyMatch(edge -> (edge.start == id || edge.end == id));
    }

}