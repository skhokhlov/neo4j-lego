package lego;

public class Edge {
    private final long start;
    private final long end;

    public Edge(long s, long t) {
        start = s;
        end = t;
    }

    public long getStart() {
        return start;
    }

    public long getEnd(){
        return end;
    }

    public boolean containsVertex(long id) {
        return start == id || end == id;
    }
}