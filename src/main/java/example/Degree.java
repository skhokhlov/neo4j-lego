package example;

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

    @Procedure(value = "example.degree", mode = Mode.READ)
    @Description("Calculate degree of vertex")
    public Stream<DegreeResults> degree(@Name("label") String label) {
        Label ind = Label.label(label);
        ResourceIterator<Node> nodes = db.findNodes(ind);

        if (nodes == null) {
            log.debug("Skipping since index does not exist: `%s`", label);
            return Stream.empty();
        }

        List<DegreeResults> a = new ArrayList<>();

        while(nodes.hasNext()) {
            final Node recordNode = nodes.next();
            DegreeResults t = new DegreeResults(recordNode.getDegree(),recordNode.getId());
            a.add(t);
        }

        return a.stream();
    }

    public class DegreeResults {
        public final Integer degree;
        public final Long nodes;

        public DegreeResults(Integer degree, Long nodes) {
            this.degree = degree;
            this.nodes = nodes;
        }

    }
}
