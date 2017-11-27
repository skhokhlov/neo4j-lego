package lego;

import lego.Algorithms.Closeness;
import lego.Results.ClosenessResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.internal.GraphDatabaseAPI;
import org.neo4j.procedure.*;

import java.util.stream.Stream;

public class ClosenessProcedure {
    @Context
    public GraphDatabaseService db;

//    @Context
//    public GraphDatabaseAPI api;

    @Procedure(value = "lego.closeness", mode = Mode.READ)
    @Description("Calculate Closeness centrality")
    public Stream<ClosenessResult> closeness(@Name("label") String label) {
//        final Graph graph = new GraphLoader(db.getAllRelationships()).withLabel(label).load();
        Graph graph;
        try (Transaction tx = db.beginTx()) {
            graph = new GraphLoader(db.getAllRelationships()).withLabel(label).load();
            tx.success();
        } catch (Exception e) {
            throw e;
        }
        return new Closeness().getScores(graph);
    }
}
