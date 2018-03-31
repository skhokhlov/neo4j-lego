package lego.Algorithms;

import lego.Graph;
import lego.RandomGraph;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class CentralityBenchmark {
    static Graph graph = new RandomGraph().withSize(500).getGraph();

    @Benchmark
    public void bench(){
        new Betweenness().getScores(graph);
    }

    @Benchmark
    public void bench2(){
        new Betweenness().getScoresOld(graph);
    }

    @Benchmark
    public void bench3(){
        new Closeness().getScores(graph);
    }

    public static void main() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + CentralityBenchmark.class.getSimpleName() + ".*")
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
