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
public class RandomGraphBenchmark {

    @Benchmark
    public Object graph() {
        return new RandomGraph().withSize(50).getGraph().size();
    }
}
