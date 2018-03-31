package lego;

import java.util.Random;

/**
 * Generates a random graph.
 */
public class RandomGraph {
    private Graph graph = new Graph();
    private int size = -1;

    /**
     * Set size of a random graph
     *
     * @param size This is size of the graph
     * @return Returns this
     */
    public RandomGraph withSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Graph size cannot be negative.");
        } else {
            this.size = size;
        }
        return this;
    }

    /**
     * Get generated graph. If size was not set it will generate a random int for the size.
     *
     * @return Returns generated random graph
     */
    public Graph getGraph() {
        Random random = new Random();

        if (size == -1) {
            size = random.nextInt();
        }

        for (int i = 0; i < size; i++) {
            this.graph.addEdge(new Edge(random.nextInt(size), random.nextInt(size)));
        }

        return this.graph;
    }
}
