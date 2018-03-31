package lego;

import org.junit.Test;

import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class RandomGraphTest {

    @Test
    public void getGraph() throws Exception {
        Graph graph = new RandomGraph().withSize(10).getGraph();
        assertEquals(10, graph.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowNegativeSize() throws Exception {
        Graph graph = new RandomGraph().withSize(-10).getGraph();
    }
}