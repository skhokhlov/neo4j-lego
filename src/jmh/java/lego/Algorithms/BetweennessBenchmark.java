package lego.Algorithms;

import lego.Edge;
import lego.Graph;
import lego.ProgressTimer;
import lego.RandomGraph;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Threads(1)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BetweennessBenchmark {
    private static Graph graph = new Graph();
    private static int lastNode = 0;

    @Setup
    public void setup() {
        try (ProgressTimer start = ProgressTimer.start(l -> System.out.println("setup took " + l + "ms"))) {
//            graph = new RandomGraph().withSize(50).getGraph();
            createNet(50);
        }
    }

    private static void createNet(int size) {
        List<Integer> temp = null;
        for (int i = 0; i < size; i++) {
            List<Integer> line = createLine(size);
            if (null != temp) {
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        if (j == k) {
                            continue;
                        }
                        graph.addEdge(new Edge(temp.get(j), line.get(k)));
                    }
                }
            }
            temp = line;
        }

    }

    private static List<Integer> createLine(int length) {
        ArrayList<Integer> nodes = new ArrayList<>();
        int temp = lastNode++;
        nodes.add(temp);
        for (int i = 1; i < length; i++) {
            int node = lastNode++;
            nodes.add(node);
            graph.addEdge(new Edge(temp, node));
            temp = node;
        }
        return nodes;
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
