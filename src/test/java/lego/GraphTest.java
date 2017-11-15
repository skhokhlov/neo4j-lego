package lego;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class GraphTest {
    @Test
    public void shouldAddEdgeToGraph() throws Exception {
        Graph graph = new Graph();
        Edge edge = new Edge(1,2);
        graph.addEdge(edge);
        assertThat(graph.containsEdge(edge), equalTo(true));
    }

    @Test
    public void shouldRemoveEdgeFromGraph() throws Exception {
        Graph graph = new Graph();
        Edge edge = new Edge(1,2);
        graph.addEdge(edge);
        graph.removeEdge(edge);
        assertThat(graph.containsEdge(edge), equalTo(false));
    }

    @Test
    public void shouldCheckThatGraphContainsVertex() throws Exception {
        Graph graph = new Graph();
        Edge edge = new Edge(1,2);
        graph.addEdge(edge);
        assertThat(graph.containsVertex(1), equalTo(true));
    }

    @Test
    public void shouldCheckThatGraphDoNotContainsVertex() throws Exception {
        Graph graph = new Graph();
        Edge edge = new Edge(1,2);
        assertThat(graph.containsVertex(9999), equalTo(false));
    }

    @Test
    public void shouldCheckThatGraphContainsEdge() throws Exception {
        Graph graph = new Graph();
        Edge edge = new Edge(1,2);
        graph.addEdge(edge);
        assertThat(graph.containsEdge(edge), equalTo(true));
    }

    @Test
    public void shouldCheckThatGraphDoNotContainsEdge() throws Exception {
        Graph graph = new Graph();
        Edge tempEdge = new Edge(2,3);
        assertThat(graph.containsEdge(tempEdge), equalTo(false));
    }

    @Test
    public void shouldReturnParallelStream() throws Exception {
        Graph graph = new Graph();
        assertThat(graph.getParallelStream().isParallel(), equalTo(true));
    }
}