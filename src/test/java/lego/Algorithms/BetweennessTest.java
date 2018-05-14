package lego.Algorithms;

import lego.Example;
import lego.Graph;
import lego.RandomGraph;
import lego.Results.CentralityResult;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class BetweennessTest {
    private Example example = new Example();

    @Test
    public void getVertexScore() {
        Graph graph = example.getGraph();
        assertThat(new Betweenness().getVertexScore(graph, 0), equalTo(0d));
        assertThat(new Betweenness().getVertexScore(graph, 1), equalTo(1d));
        assertThat(new Betweenness().getVertexScore(graph, 4), equalTo(1.5));
    }

    @Test
    public void shouldCalculateBetweennessForEachVertex() {
        Graph graph = example.getGraph();
//        Graph graph = new RandomGraph().withSize(10).getGraph();

        Stream<CentralityResult> res = Stream.of(
//                new CentralityResult(0, 0),
                new CentralityResult(1, 1),
                new CentralityResult(2, 0.5),
//                new CentralityResult(3, 0),
                new CentralityResult(4, 1.5)
        );
        assertThat(
                new Betweenness().getScores(graph).mapToDouble(score -> score.centrality).sorted().toArray(),
                equalTo(res.mapToDouble(score -> score.centrality).sorted().toArray())
        );
    }

    @Test
    public void shouldWorkCorrectlyIfGraphIsEmpty() {
        Graph graph = new Graph();
        assertThat(new Betweenness().getScores(graph).count(), equalTo(0L));
    }

    @Test
    public void shouldWorkWithRandomGraph() {
        Graph graph = new RandomGraph().withSize(5).getGraph();
        assertNotNull(new Betweenness().getScores(graph));
    }
}