package lego.Algorithms;

import lego.Edge;
import lego.Example;
import lego.Graph;
import lego.Results.CentralityResult;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class BetweennessTest {
    private Example example = new Example();

    @Test
    public void getVertexScore() throws Exception {
        Graph graph = example.getGraph();
        assertThat(new Betweenness().getVertexScore(graph, 0), equalTo(0d));
        assertThat(new Betweenness().getVertexScore(graph, 1), equalTo(1d));
        assertThat(new Betweenness().getVertexScore(graph, 4), equalTo(1.5));
    }

    @Test
    public void shouldCalculateClosenessForEachVertex() throws Exception {
        Graph graph = example.getGraph();

        Stream<CentralityResult> res = Stream.of(
                new CentralityResult(0, 0),
                new CentralityResult(1, 1),
                new CentralityResult(2, 0.5),
                new CentralityResult(3, 0),
                new CentralityResult(4, 1.5)
        );
        assertThat(
                new Betweenness().getScores(graph).mapToDouble(score -> score.centrality).sorted().toArray(),
                equalTo(res.mapToDouble(score -> score.centrality).sorted().toArray())
        );
    }

    @Test
    public void shouldWorkCorrectlyIfGraphIsEmpty() throws Exception {
        Graph graph = new Graph();
        assertThat(new Betweenness().getScores(graph).count(), equalTo(0L));
    }
}