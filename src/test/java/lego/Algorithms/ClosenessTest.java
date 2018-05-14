package lego.Algorithms;

import lego.Edge;
import lego.Graph;
import lego.RandomGraph;
import lego.Results.CentralityResult;
import org.junit.Test;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class ClosenessTest {
    @Test
    public void getVertexScore() {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 1);
        Edge edge3 = new Edge(3, 1);
        graph.addEdge(edge1).addEdge(edge2).addEdge(edge3);
        assertThat(new Closeness().getVertexScore(graph, 3), equalTo(1d / 3d));
    }

    @Test
    public void shouldCalculateClosenessForEachVertex() {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 1);
        Edge edge3 = new Edge(3, 1);
        graph.addEdge(edge1).addEdge(edge2).addEdge(edge3);
        Supplier<Stream<CentralityResult>> streamSupplier = () -> Stream.of(
                new CentralityResult(1, 1),
                new CentralityResult(2, 1),
                new CentralityResult(3, 1d / 3d)
        );
        assertThat(
                new Closeness().getScores(graph).mapToDouble(score -> score.centrality).sorted().toArray(),
                equalTo(streamSupplier.get().mapToDouble(score -> score.centrality).sorted().toArray())
        );
        assertThat(
                new Closeness().getScoresOld(graph).mapToDouble(score -> score.centrality).sorted().toArray(),
                equalTo(streamSupplier.get().mapToDouble(score -> score.centrality).sorted().toArray())
        );
    }

    @Test
    public void shouldWorkCorrectlyIfGraphIsEmpty() {
        Graph graph = new Graph();
        assertThat(new Closeness().getScores(graph).count(), equalTo(0L));
    }

    @Test
    public void shouldWorkWithRandomGraph() {
        Graph graph = new RandomGraph().withSize(5).getGraph();
        assertNotNull(new Closeness().getScores(graph));
    }

}