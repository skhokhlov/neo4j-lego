package lego.Results;

public class ClosenessResult {
    public final long nodes;
    public final double degree;

    /**
     * @param nodes  node id
     * @param degree node score
     */
    public ClosenessResult(long nodes, double degree) {
        this.degree = degree;
        this.nodes = nodes;
    }

}