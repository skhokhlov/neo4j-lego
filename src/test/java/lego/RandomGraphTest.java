package lego;

import org.junit.Test;
import static org.junit.Assert.*;

public class RandomGraphTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowNegativeSize() {
        new RandomGraph().withSize(-10).getGraph();
    }

    @Test
    public void shouldGenerateCorrectEdges() {
        final int size = 50;
        final int vertices = (int) Math.pow(size, 2);
        Graph graph = new RandomGraph().withSize(size).getGraph();

        assertEquals((int) Math.pow(size, 3), graph.size());

        graph.getStream().forEach(edge -> {
            assertTrue(edge.getStart() < vertices);
            assertTrue(edge.getStart() >= 0);
            assertTrue(edge.getEnd() < vertices);
            assertTrue(edge.getEnd() >= 0);
        });
    }
}