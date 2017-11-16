package lego;

/**
 * Class for edges in graph. Edge cannot be changed.
 */
public class Edge {
    private final long start;
    private final long end;

    /**
     * Edge constructor
     *
     * @param start start vertex
     * @param target target vertex
     */
    public Edge(long start, long target) {
        this.start = start;
        this.end = target;
    }

    /**
     * @return start vertex
     */
    public long getStart() {
        return start;
    }

    /**
     * @return target vertex
     */
    public long getEnd(){
        return end;
    }

    /**
     * Check that that edge contains vertex
     *
     * @param id id of vertex
     * @return boolean
     */
    public boolean containsVertex(long id) {
        return start == id || end == id;
    }
}