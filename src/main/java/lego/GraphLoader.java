package lego;

import org.neo4j.graphdb.GraphDatabaseService;

public class GraphLoader {
    private String label;
    private Graph graph;

    public GraphLoader(GraphDatabaseService db) {
    }

    public GraphLoader withLabel(String label) {
        this.label = label;
        return this;
    }

    public Graph load() {
        return graph;
    }
}
