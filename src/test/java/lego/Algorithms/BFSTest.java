package lego.Algorithms;

import lego.Edge;
import lego.Example;
import lego.Graph;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class BFSTest {
    private Example example = new Example();

    @Test
    public void getPathLength() throws Exception {
        Graph graph = example.getGraph();
        assertThat(new BFS(graph).getPathLength(1, 3), equalTo(2L));
    }

    @Test
    public void getAllShortestPaths() throws Exception {
        Graph graph = example.getGraph();
        assertThat(new BFS(graph).getAllShortestPaths(3, 1).count(), equalTo(0L));
        assertThat(new BFS(graph).getAllShortestPaths(0, 1).count(), equalTo(1L));
        assertThat(new BFS(graph).getAllShortestPaths(1, 3).count(), equalTo(2L));
    }

}