package lego.Algorithms;

import lego.Edge;
import lego.Graph;
import lego.ProgressTimer;
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
public class BetweennessBenchmark {
    private static Graph graph = new Graph();

    @Setup
    public void setup() {
        try (ProgressTimer start = ProgressTimer.start(l -> System.out.println("setup took " + l + "ms"))) {
//            graph = new RandomGraph().withSize(50).getGraph();
            createNet(50);
        }
    }

    private static void createNet(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if (j == k) {
                        continue;
                    }
                    graph.addEdge(new Edge(i + j, i + k));
                }
            }
        }
    }

    @Benchmark
    public Object betweenness() {
        return new Betweenness().getScores(graph).count();
    }

//    @Benchmark
//    public Object betweennessOld(){
//        return new Betweenness().getScoresOld(graph).count();
//    }
}
