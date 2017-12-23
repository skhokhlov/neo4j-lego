package lego;

import lego.Algorithms.Closeness;
import lego.Results.CentralityResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.procedure.*;

import java.util.stream.Stream;

public class ClosenessProcedure {
    @Context
    public GraphDatabaseService db;

//    @Context
//    public GraphDatabaseAPI api;

    @Procedure(value = "lego.closeness", mode = Mode.READ)
    @Description("Calculate Closeness centrality")
    public Stream<CentralityResult> closeness(@Name("label") String label) {
        Graph graph = new GraphLoader(db, label).load();
        return new Closeness().getScores(graph);
    }

}
