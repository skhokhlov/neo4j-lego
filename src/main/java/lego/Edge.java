package lego;

/**
 * Class for edges in {@link Graph}. Edge cannot be changed.
 */
public class Edge {
    private final long start;
    private final long end;

    /**
     * Edge constructor
     *
     * @param start  This is start vertex of the edge
     * @param target This is target vertex of the edge
     */
    public Edge(long start, long target) {
        this.start = start;
        this.end = target;
    }

    /**
     * Get start vertex of the edge
     *
     * @return Start vertex
     */
    public long getStart() {
        return start;
    }

    /**
     * Get target vertex of the edge
     *
     * @return Target vertex
     */
    public long getEnd() {
        return end;
    }

    /**
     * Check that that edge contains vertex
     *
     * @param id This is id of vertex
     * @return True if contains and False of not
     */
    public boolean containsVertex(long id) {
        return start == id || end == id;
    }
}