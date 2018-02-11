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
        assertThat(new BFS(graph).getPathLength(1, 1), equalTo(0L));
    }

    @Test
    public void findAllShortestPaths() throws Exception {
        Graph graph = example.getGraph();
        assertThat(new BFS(graph).findAllShortestPaths(0, 0).count(), equalTo(0L));
        assertThat(new BFS(graph).findAllShortestPaths(3, 1).count(), equalTo(0L));
        assertThat(new BFS(graph).findAllShortestPaths(0, 1).count(), equalTo(1L));
        assertThat(new BFS(graph).findAllShortestPaths(0, 3).count(), equalTo(1L));
        assertThat(new BFS(graph).findAllShortestPaths(1, 3).count(), equalTo(2L));
    }

}