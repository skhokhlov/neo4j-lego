package lego.Algorithms;

import lego.Direction;
import lego.Edge;
import lego.Graph;
import lego.Results.DegreeResult;
import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class DegreeTest {
    @Test
    public void shouldSetBothDirection() {
        Degree degree = new Degree().setDirection(Direction.BOTH);
        assertThat(degree.getDirection(), equalTo(Direction.BOTH));
    }
    
    @Test
    public void shouldSetIncomingDirection() {
        Degree degree = new Degree().setDirection(Direction.INCOMING);
        assertThat(degree.getDirection(), equalTo(Direction.INCOMING));
    }
    
    @Test
    public void shouldSetOutgoingDirection() {
        Degree degree = new Degree().setDirection(Direction.OUTGOING);
        assertThat(degree.getDirection(), equalTo(Direction.OUTGOING));
    }

    @Test
    public void shouldCalculateVertexDegreeForBoth() {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 1);
        graph.addEdge(edge1).addEdge(edge2);
        assertThat(new Degree().setDirection(Direction.BOTH).getVertexScore(graph, 1), equalTo(2));
    }

    @Test
    public void shouldCalculateVertexDegreeForIncoming() {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(1, 3);
        graph.addEdge(edge1).addEdge(edge2);
        assertThat(new Degree().setDirection(Direction.INCOMING).getVertexScore(graph, 1), equalTo(0));
    }

    @Test
    public void shouldCalculateVertexDegreeForOutgoing() {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(1, 3);
        graph.addEdge(edge1).addEdge(edge2);
        assertThat(new Degree().setDirection(Direction.OUTGOING).getVertexScore(graph, 1), equalTo(2));
    }

    @Test
    public void shouldCalculateZeroVertexDegree() {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1, 2);
        graph.addEdge(edge1);
        assertThat(new Degree().setDirection(Direction.OUTGOING).getVertexScore(graph, 2), equalTo(0));
    }

    @Test
    public void shouldCalculateDegreeForEachVertex() {
        Graph graph = new Graph();
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(1, 3);
        graph.addEdge(edge1).addEdge(edge2);
        Stream<DegreeResult> res = Stream.of(
                new DegreeResult(1, 2),
                new DegreeResult(2, 1),
                new DegreeResult(3, 1)
        );
        assertThat(
                new Degree().setDirection(Direction.BOTH).getScores(graph).mapToLong(score -> score.degree).sorted().toArray(),
                equalTo(res.mapToLong(score -> score.degree).sorted().toArray())
        );
    }

}