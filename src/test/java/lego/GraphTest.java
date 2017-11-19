package lego;

import org.junit.Test;
import org.neo4j.cypher.internal.frontend.v2_3.ast.functions.E;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void shouldAddEdgeToGraphWithPipeline() throws Exception {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1,2);
        Edge edge2 = new Edge(3,4);
        graph.addEdge(edge1).addEdge(edge2);
        assertThat(graph.containsEdge(edge1)&&graph.containsEdge(edge2), equalTo(true));
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
    public void shouldReturnStream() throws Exception {
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

    @Test
    public void shouldReturnListWithAdjacentVertices() throws Exception {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1,2);
        Edge edge2 = new Edge(1,3);
        Edge edge3 = new Edge(2,4);
        graph.addEdge(edge1).addEdge(edge2).addEdge(edge3);
        List<Long> res = new ArrayList<>();
        res.add(2L);
        res.add(3L);
        assertThat(graph.getAdjacentVertices(1), equalTo(res));
    }

    @Test
    public void shouldReturnListWithOutgoingEdges() throws Exception {
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
    public void shouldReturnListWithIncidentEdges() throws Exception {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1,2);
        Edge edge2 = new Edge(2,1);
        graph.addEdge(edge1).addEdge(edge2);
        List<Edge> res = new ArrayList<>();
        res.add(edge1);
        res.add(edge2);
        assertThat(graph.getIncidentEdges(1), equalTo(res));
    }
}