package lego.Algorithms;

import lego.Edge;
import lego.Graph;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class ClosenessTest {
    @Test
    public void getVertexScore() throws Exception {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 1);
        Edge edge3 = new Edge(3, 1);
        graph.addEdge(edge1).addEdge(edge2).addEdge(edge3);
        assertThat(new Closeness().getVertexScore(graph, 3), equalTo(0.5));
    }

}