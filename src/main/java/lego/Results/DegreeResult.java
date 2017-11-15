package lego.Results;

public class DegreeResult {
    public final long nodes;
    public final long degree;

    /**
     * @param nodes  node id
     * @param degree node degree
     */
    public DegreeResult(long nodes, long degree) {
        this.degree = degree;
        this.nodes = nodes;
    }

}