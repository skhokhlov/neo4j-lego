package lego;

import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.harness.junit.Neo4jRule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class GraphLoaderTest {
    final private Example example = new Example();

    @Rule
    public Neo4jRule neo4j = new Neo4jRule();

    @Test
    public void shouldSetLabel() throws Exception {
        try (
                Driver driver = GraphDatabase.driver(
                        neo4j.boltURI(),
                        Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig()
                );
                Session session = driver.session()
        ) {
            session.run(example.getGraphStatement());
            GraphLoader loader = new GraphLoader(neo4j.getGraphDatabaseService(), example.getLabel()).withLabel(example.getLabel());

            assertThat(loader.getLabel(), equalTo(example.getLabel()));
        }
    }

    @Test
    public void shouldLoadGraph() throws Exception {
        try (
                Driver driver = GraphDatabase.driver(
                        neo4j.boltURI(),
                        Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig()
                );
                Session session = driver.session()
        ) {
            session.run(example.getGraphStatement());
            int a = session.run("Match (n) return n").list().size(); // If it in not here test not works

            Graph graph = new GraphLoader(neo4j.getGraphDatabaseService(), example.getLabel()).load();
            assertThat(graph.getVertexStream().sorted().toArray(), equalTo(example.getGraph().getVertexStream().sorted().toArray()));

        }
    }
}
