package lego.Algorithms;

import lego.Edge;
import lego.Graph;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class BFSTest {
    @Test
    public void getPathLength() throws Exception {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 1);
        Edge edge3 = new Edge(2, 3);
        Edge edge4 = new Edge(3, 4);
        Edge edge5 = new Edge(2, 5);
        graph.addEdge(edge1).addEdge(edge2).addEdge(edge3).addEdge(edge4).addEdge(edge5);
        assertThat(new BFS(graph).getPathLength(1, 4), equalTo(3L));
    }

    @Test
    public void getAllShortestPaths() throws Exception {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 1);
        Edge edge3 = new Edge(2, 3);
        Edge edge4 = new Edge(3, 4);
        Edge edge5 = new Edge(2, 5);
        graph.addEdge(edge1).addEdge(edge2).addEdge(edge3).addEdge(edge4).addEdge(edge5);
        assertThat(new BFS(graph).getAllShortestPaths(3, 1).count(), equalTo(2L));
    }

}