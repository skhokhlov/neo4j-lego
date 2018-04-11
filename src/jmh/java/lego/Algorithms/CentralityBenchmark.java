package lego.Algorithms;

import lego.Graph;
import lego.RandomGraph;
import lego.Results.CentralityResult;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class CentralityBenchmark {
    final static Graph graph = new RandomGraph().withSize(10).getGraph();

    @Benchmark
    public void betweenness(){
        List<CentralityResult> r = new Betweenness().getScores(graph).collect(Collectors.toList());
    }

    @Benchmark
    public void betweennessOld(){
        List<CentralityResult> r = new Betweenness().getScoresOld(graph).collect(Collectors.toList());
    }

    @Benchmark
    public void closeness(){
        List<CentralityResult> r = new Closeness().getScores(graph).collect(Collectors.toList());
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
