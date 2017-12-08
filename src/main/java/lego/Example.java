package lego;

import lego.Algorithms.Closeness;
import lego.Results.ClosenessResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Example graph for tests.
 * Nodes label: User. Vertices: A, B, C, D, E.
 * Relationships: A-TO-B, B-TO-C, B-TO-E, C-TO-D, E-TO-D.
 */
public class Example {
    private String label;
    private String exampleGraphStatement;
    private Graph exampleGraph;
    private List<ClosenessResult> closeness;

    public Example() {
        exampleGraph = new Graph().addEdge(new Edge(0, 1))
                .addEdge(new Edge(1, 2))
                .addEdge(new Edge(1, 4))
                .addEdge(new Edge(2, 3))
                .addEdge(new Edge(4, 3));
        closeness = new Closeness().getScores(exampleGraph).collect(Collectors.toList());
        exampleGraphStatement = "CREATE (A:User {name: \"A\"})\n" +
                "CREATE (B:User {name: \"B\"})\n" +
                "CREATE (C:User {name: \"C\"})\n" +
                "CREATE (D:User {name: \"D\"})\n" +
                "CREATE (E:User {name: \"E\"})\n" +
                "CREATE (A)-[:TO]->(E)\n" +
                "CREATE (A)-[:TO]->(B)\n" +
                "CREATE (B)-[:TO]->(C)\n" +
                "CREATE (B)-[:TO]->(E)\n" +
                "CREATE (C)-[:TO]->(D)\n" +
                "CREATE (E)-[:TO]->(D)";
        label = "User";
    }

    public String getGraphStatement() {
        return exampleGraphStatement;
    }

    public String getLabel() {
        return label;
    }

    public Graph getGraph() {
        return exampleGraph;
    }

    public List<ClosenessResult> getCloseness() {
        return closeness;
    }
}
