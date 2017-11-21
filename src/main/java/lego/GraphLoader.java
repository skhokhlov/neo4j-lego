package lego;

import org.neo4j.graphdb.GraphDatabaseService;

/**
 * Interface for providing {@link Graph}.
 */
public class GraphLoader {
    private GraphDatabaseService db;
    private String label;
    private Graph graph;

    public GraphLoader(GraphDatabaseService db) {
        this.db = db;
    }

    public GraphLoader withLabel(String label) {
        this.label = label;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Graph load() {
        return graph;
    }
}
