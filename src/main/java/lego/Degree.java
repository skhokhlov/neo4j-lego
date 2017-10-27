package lego;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.procedure.*;

import java.util.stream.Stream;

public class Degree {
    @Context
    public GraphDatabaseService db;

    /**
     * Calculate degree of vertex
     *
     * @param label label name
     */
    @Procedure(value = "lego.degree", mode = Mode.READ)
    @Description("Calculate degree of vertex")
    public Stream<DegreeResults> degree(@Name("label") String label) {
        final Label ind = Label.label(label);
        return db.findNodes(ind).stream().map((n) -> new DegreeResults(n.getId(), n.getDegree()));
    }

    public class DegreeResults {
        public final long nodes;
        public final long degree;

        /**
         * @param nodes  node id
         * @param degree node degree
         */
        public DegreeResults(long nodes, long degree) {
            this.degree = degree;
            this.nodes = nodes;
        }

    }
}
