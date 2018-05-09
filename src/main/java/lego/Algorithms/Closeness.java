package lego.Algorithms;

import lego.Direction;
import lego.Graph;
import lego.Results.CentralityResult;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Closeness {
    private Direction direction = Direction.OUTGOING;

    public Direction getDirection() {
        return direction;
    }

    /**
     * Set direction param
     * TODO
     *
     * @param direction edge directions for degree calculation
     * @return Direction
     */
    public Closeness setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    /**
     * Calculate closeness centrality for specific vertex in graph using {@link BFS}.
     *
     * @param graph    This is graph for calculations
     * @param vertexId This is id of the vertex
     * @return Centrality score for vertex
     */
    public double getVertexScore(Graph graph, int vertexId) {
        if (!graph.containsVertex(vertexId)) {
            throw new IllegalArgumentException("Graph do not contains this vertex");
        }

        BFS bfs = new BFS();

        long sum = graph.getVertexStream().mapToLong(vertex -> bfs.getPathLength(graph, vertexId, vertex)).sum();

        return 1 / (double) sum;
    }

    /**
     * Calculate closeness centrality for each vertex in graph
     * and return stream of {@link CentralityResult} class.
     *
     * @param graph This is graph for calculations
     * @return Stream of {@link CentralityResult} with scores
     */
    public Stream<CentralityResult> getScores(Graph graph) {
//        return graph.getParallelVertexStream().map(vertex -> new CentralityResult(vertex, getVertexScore(graph, vertex)));
        return calc(graph);
    }

    /**
     * https://pdfs.semanticscholar.org/7d1b/c639258ad49af9e51199902313c3a2149c63.pdf
     * @param graph
     * @return
     */
    private Stream<CentralityResult> calc(Graph graph) {
        final int verticesCount = graph.getVerticesCount();
        ConcurrentHashMap<Integer, Double> map1 = new ConcurrentHashMap<>(verticesCount);

        graph.getParallelVertexStream().forEach((source) -> {
            Queue<Integer> queue = new LinkedList<>(); // Q
            Map<Integer, Integer> distance = new HashMap<>(verticesCount); // d
            Map<Integer, Integer> far = new HashMap<>(verticesCount);

            queue.add(source);
            distance.put(source, 0);
            far.put(source, 0);

            while (!queue.isEmpty()) {
                int u = queue.poll();

                for (int w : graph.getAdjacentVertices(u)) {
                    if (!distance.containsKey(w)) {
                        queue.add(w);
                        distance.put(w, distance.get(u) + 1);
                        far.put(source, far.get(source) + distance.get(w));
                    }
                }
            }

            map1.put(source, 1 / (double) far.get(source));
        });

        ArrayList<CentralityResult> res = new ArrayList<>(map1.size());
        map1.forEach((integer, aDouble) -> res.add(new CentralityResult(integer, aDouble)));
        return res.stream();
    }
}
