package lego;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public boolean containsEdge(Edge edge) {
        return this.getStream().anyMatch(edge1 -> edge == edge1);
    }

    public boolean containsVertex(long id) {
        return this.getStream().anyMatch(edge -> (edge.start == id || edge.end == id));
    }

}