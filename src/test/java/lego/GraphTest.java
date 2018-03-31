package lego;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class GraphTest {
    @Test
    public void shouldAddEdgeToGraph() {
        Graph graph = new Graph();
        Edge edge = new Edge(1,2);
        graph.addEdge(edge);
        assertTrue(graph.containsEdge(edge));
    }
    
    @Test
    public void shouldAddEdgeToGraphWithPipeline() {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1,2);
        Edge edge2 = new Edge(3,4);
        graph.addEdge(edge1).addEdge(edge2);
        assertTrue(graph.containsEdge(edge1)&&graph.containsEdge(edge2));
    }

    @Test
    public void shouldRemoveEdgeFromGraph() {
        Graph graph = new Graph();
        Edge edge = new Edge(1,2);
        graph.addEdge(edge);
        graph.removeEdge(edge);
        assertFalse(graph.containsEdge(edge));
    }

    @Test
    public void shouldReturnStream() {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1,2);
        Edge edge2 = new Edge(1,3);
        Edge edge3 = new Edge(2,1);
        graph.addEdge(edge1).addEdge(edge2).addEdge(edge3);
        List<Edge> res = new ArrayList<>();
        res.add(edge1);
        res.add(edge2);
        res.add(edge3);
        assertThat(graph.getStream().collect(Collectors.toList()), equalTo(res));

    }

    @Test
    public void shouldCheckThatGraphContainsVertex() {
        Graph graph = new Graph();
        Edge edge = new Edge(1,2);
        graph.addEdge(edge);
        assertTrue(graph.containsVertex(1));
    }

    @Test
    public void shouldCheckThatGraphDoNotContainsVertex() {
        Graph graph = new Graph();
        Edge edge = new Edge(1,2);
        assertFalse(graph.containsVertex(9999));
    }

    @Test
    public void shouldCheckThatGraphContainsEdge() {
        Graph graph = new Graph();
        Edge edge = new Edge(1,2);
        graph.addEdge(edge);
        assertTrue(graph.containsEdge(edge));
    }

    @Test
    public void shouldCheckThatGraphDoNotContainsEdge() {
        Graph graph = new Graph();
        Edge tempEdge = new Edge(2,3);
        assertFalse(graph.containsEdge(tempEdge));
    }

    @Test
    public void shouldReturnParallelStream() {
        Graph graph = new Graph();
        assertTrue(graph.getParallelStream().isParallel());
    }

    @Test
    public void shouldReturnListWithAdjacentVertices() {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1,2);
        Edge edge2 = new Edge(1,3);
        Edge edge3 = new Edge(2,4);
        graph.addEdge(edge1).addEdge(edge2).addEdge(edge3);
        List<Integer> res = new ArrayList<>();
        res.add(2);
        res.add(3);
        assertThat(graph.getAdjacentVertices(1), equalTo(res));
        List<Integer> res2 = new ArrayList<>();
        res2.add(4);
        assertThat(graph.getAdjacentVertices(2), equalTo(res2));
        List<Integer> res3 = new ArrayList<>();
        assertThat(graph.getAdjacentVertices(3), equalTo(res3));
    }

    @Test
    public void shouldReturnListWithOutgoingEdges() {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1,2);
        Edge edge2 = new Edge(1,3);
        Edge edge3 = new Edge(2,1);
        graph.addEdge(edge1).addEdge(edge2).addEdge(edge3);
        List<Edge> res = new ArrayList<>();
        res.add(edge1);
        res.add(edge2);
        assertThat(graph.getOutgoingEdges(1), equalTo(res));
    }

    @Test
    public void shouldReturnListWithIncidentEdges() {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1,2);
        Edge edge2 = new Edge(2,1);
        graph.addEdge(edge1).addEdge(edge2);
        List<Edge> res = new ArrayList<>();
        res.add(edge1);
        res.add(edge2);
        assertThat(graph.getIncidentEdges(1), equalTo(res));
    }

    @Test
    public void shouldReturnCorrectVerticesCount() {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1,2);
        Edge edge2 = new Edge(2,1);
        graph.addEdge(edge1).addEdge(edge2);
        assertThat(graph.getVerticesCount(), equalTo(2));
    }
}