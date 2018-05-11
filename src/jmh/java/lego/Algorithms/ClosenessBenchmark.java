package lego.Algorithms;

import lego.Graph;
import lego.RandomGraph;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@Threads(1)
@Fork(2)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ClosenessBenchmark {
    private Graph graph;

    @Setup
    public void setup() {
        graph  = new RandomGraph().withSize(10).getGraph();
    }

    @Benchmark
    public Object closeness(){
       return new Closeness().getScores(graph).count();
    }
}
