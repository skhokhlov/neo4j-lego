package lego.Results;

public class ClosenessResult {
    public final long nodes;
    public final double centrality;

    /**
     * @param nodes  node id
     * @param centrality node score
     */
    public ClosenessResult(long nodes, double centrality) {
        this.centrality = centrality;
        this.nodes = nodes;
    }

}