package lego.Algorithms;

import lego.Example;
import lego.Graph;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class BFSTest {
    private Example example = new Example();

    @Test
    public void getPathLength() {
        Graph graph = example.getGraph();
        assertThat(new BFS().getPathLength(graph,1, 3), equalTo(2));
        assertThat(new BFS().getPathLength(graph, 1, 1), equalTo(0));
    }

    @Test
    public void findAllShortestPaths() {
        Graph graph = example.getGraph();
        assertThat(new BFS().findAllShortestPaths(graph,0, 0).count(), equalTo(0L));
        assertThat(new BFS().findAllShortestPaths(graph,3, 1).count(), equalTo(0L));
        assertThat(new BFS().findAllShortestPaths(graph,0, 1).count(), equalTo(1L));
        assertThat(new BFS().findAllShortestPaths(graph,0, 3).count(), equalTo(1L));
        assertThat(new BFS().findAllShortestPaths(graph,1, 3).count(), equalTo(2L));
    }

}