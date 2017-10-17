package example;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.logging.Log;
import org.neo4j.procedure.*;

import java.util.stream.Stream;

public class Degree {
    @Context
    public GraphDatabaseService db;

    @Context
    public Log log;

//    private static final Map<String, >

    @Procedure(value = "example.degree", mode = Mode.READ)
    @Description("Calculate degree of vertex")
    public Stream<DegreeResults> degree(@Name("label") String label) {
        String index = "label-" + label;

        if (!db.index().existsForNodes(index)) {
            log.debug("Skipping since index does not exist: `%s`", index);
            return Stream.empty();
        }

        Label ind = Label.label(label);

        return db.findNodes(ind).stream().map(DegreeResults::new);
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
