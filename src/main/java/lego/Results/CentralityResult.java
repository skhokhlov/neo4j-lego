package lego.Results;

public class CentralityResult {
    public final long nodes;
    public final double centrality;

    /**
     * @param nodes      node id
     * @param centrality node score
     */
    public CentralityResult(long nodes, double centrality) {
        this.centrality = centrality;
        this.nodes = nodes;
    }
}