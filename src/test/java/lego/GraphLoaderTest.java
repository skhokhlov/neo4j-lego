package lego;

import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.v1.*;
import org.neo4j.graphdb.Transaction;
import org.neo4j.harness.junit.Neo4jRule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class GraphLoaderTest {
    final private Example example = new Example();

    @Rule
    public Neo4jRule neo4j = new Neo4jRule();

    @Test
    public void shouldSetLabel() {
        try (
                Driver driver = GraphDatabase.driver(
                        neo4j.boltURI(),
                        Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig()
                );
                Session session = driver.session()
        ) {
            session.run(example.getGraphStatement());
            try (Transaction tx = neo4j.getGraphDatabaseService().beginTx()) {
                GraphLoader loader = new GraphLoader(neo4j.getGraphDatabaseService(), example.getLabel()).withLabel(example.getLabel());
                assertThat(loader.getLabel(), equalTo(example.getLabel()));
                tx.close();
            }
        }
    }

    @Test
    public void shouldLoadGraph() {
        try (
                Driver driver = GraphDatabase.driver(
                        neo4j.boltURI(),
                        Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig()
                );
                Session session = driver.session()
        ) {
            session.run(example.getGraphStatement());
            int a = session.run("Match (n) return n").list().size(); // If it in not here test not works
            try (Transaction tx = neo4j.getGraphDatabaseService().beginTx()) {
                Graph graph = new GraphLoader(neo4j.getGraphDatabaseService(), example.getLabel()).load();
                assertThat(
                        graph.getVertexStream().sorted().toArray(),
                        equalTo(example.getGraph().getVertexStream().sorted().toArray())
                );
                tx.close();
            }

        }
    }
}
