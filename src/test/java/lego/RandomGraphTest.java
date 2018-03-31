package lego;

import org.junit.Test;
import static org.junit.Assert.*;

public class RandomGraphTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowNegativeSize() throws Exception {
        new RandomGraph().withSize(-10).getGraph();
    }

    @Test
    public void shouldGenerateCorrectEdges() throws Exception {
        final int size = 50;
        Graph graph = new RandomGraph().withSize(size).getGraph();

        assertEquals(size, graph.size());

        graph.getStream().forEach(edge -> {
            assertTrue(edge.getStart() < size);
            assertTrue(edge.getStart() >= 0);
            assertTrue(edge.getEnd() < size);
            assertTrue(edge.getEnd() >= 0);
        });
    }
}