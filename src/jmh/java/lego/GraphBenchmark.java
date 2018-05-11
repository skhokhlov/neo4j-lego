package lego;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@Threads(1)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class GraphBenchmark {
    private Graph graph;

    @Setup
    public void setup() {
        graph  = new RandomGraph().withSize(10).getGraph();
    }

    @Benchmark
    public void adjacentVertices(){
        graph.getVertexStream().forEach(vertex -> graph.getAdjacentVertices(vertex).size());
    }

    @Benchmark
    public Object vertexStream(){
        return graph.getVertexStream().count();
    }
}
