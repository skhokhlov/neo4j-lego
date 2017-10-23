package lego;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.logging.Log;
import org.neo4j.procedure.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Degree {
    @Context
    public GraphDatabaseService db;

    @Context
    public Log log;

    /**
     * Calculate degree of vertex
     *
     * @param label label name
     */
    @Procedure(value = "lego.degree", mode = Mode.READ)
    @Description("Calculate degree of vertex")
    public Stream<DegreeResults> degree(@Name("label") String label) {
        final Label ind = Label.label(label);
        ResourceIterator<Node> nodes = db.findNodes(ind);

        List<DegreeResults> a = new ArrayList<>();

        while (nodes.hasNext()) {
            final Node recordNode = nodes.next();
            DegreeResults t = new DegreeResults(recordNode.getId(), recordNode.getDegree());
            a.add(t);
        }

        if (a.isEmpty()) {
            log.debug("Skipping since index does not exist: `%s`", label);
            return Stream.empty();
        }

        return a.stream();
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
