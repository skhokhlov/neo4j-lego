package lego;

import lego.Algorithms.Betweenness;
import lego.Results.CentralityResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.procedure.*;

import java.util.stream.Stream;

public class BetweennessProcedure {
    @Context
    public GraphDatabaseService db;

    @Procedure(value = "lego.betweenness", mode = Mode.READ)
    @Description("Calculate Betweenness centrality")
    public Stream<CentralityResult> betweenness(@Name("label") String label) {
        Graph graph = new GraphLoader(db, label).load();
        return new Betweenness().getScores(graph);
    }
}
