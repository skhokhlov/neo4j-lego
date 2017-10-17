package example;

import org.junit.Rule;
import org.junit.Test;

import org.neo4j.driver.v1.*;
import org.neo4j.harness.junit.Neo4jRule;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.neo4j.driver.v1.Values.parameters;

public class DegreeTest {
    // This rule starts a Neo4j instance
    @Rule
    public Neo4jRule neo4j = new Neo4jRule()

            // This is the function we want to test
            .withFunction(Degree.class);

    @Test
    public void shouldAllowIndexingAndFindingANode() throws Throwable {
        // In a try-block, to make sure we close the driver and session after the test
        try (
                Driver driver = GraphDatabase.driver(
                        neo4j.boltURI(),
                        Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig()
                );
                Session session = driver.session()
        ) {
            // Given I've started Neo4j with the FullTextIndex procedure class
            //       which my 'neo4j' rule above does.
            // And given I have a node in the database
            long nodeId1 = session.run("CREATE (p:User {name:'Brookreson'}) RETURN id(p)")
                    .single()
                    .get(0).asLong();

            long nodeId2 = session.run("CREATE (p:User {name:'William'}) RETURN id(p)")
                    .single()
                    .get(0).asLong();

            StatementResult result1 = session.run("CALL example.degree({id})", parameters("id", nodeId1));
//            StatementResult result2 = session.run("CALL example.degree({id})", parameters("id", nodeId2));
            assertThat(result1.single().get("degree").asInt(), equalTo(0));
        }
    }
}