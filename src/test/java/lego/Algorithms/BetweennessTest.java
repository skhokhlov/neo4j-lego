package lego.Algorithms;

import lego.Example;
import lego.Graph;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class BetweennessTest {
    private Example example = new Example();

    @Test
    public void getVertexScore() throws Exception {
        Graph graph = example.getGraph();
        assertThat(new Betweenness().getVertexScore(graph, 0), equalTo(0d));
        assertThat(new Betweenness().getVertexScore(graph, 1), equalTo(5d));
        assertThat(new Betweenness().getVertexScore(graph, 4), equalTo(1.5));
    }
}