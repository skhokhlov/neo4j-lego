package lego;

/**
 * Example graph for tests.
 * Nodes label: User. Vertices: A, B, C, D, E.
 * Relationships: A->B, B->C, B->E, C->D, E->D.
 */
public class Example {
    final private String label = "User";
    final private String exampleGraphStatement = "CREATE (A:User {name: \"A\"})\n" +
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
    final private Graph exampleGraph = new Graph().addEdge(new Edge(1, 2))
            .addEdge(new Edge(2, 3))
            .addEdge(new Edge(2, 5))
            .addEdge(new Edge(3, 4))
            .addEdge(new Edge(5, 4));

    public String getGraphStatement() {
        return exampleGraphStatement;
    }

    public String getLabel() {
        return label;
    }

    public Graph getGraph() {
        return exampleGraph;
    }
}
