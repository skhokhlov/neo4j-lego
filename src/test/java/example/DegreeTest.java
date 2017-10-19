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
    public void shouldCalculateDegree() throws Throwable {
        // In a try-block, to make sure we close the driver and session after the test
        try (
                Driver driver = GraphDatabase.driver(
                        neo4j.boltURI(),
                        Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig()
                );
                Session session = driver.session()
        ) {
            String label = "User";
            session.run("CREATE (p:User {name:'Brookreson'})", parameters("label", label));

            StatementResult result1 = session.run("CALL example.degree('{label}')", parameters("label", label));

            assertThat(result1.single().get("degree").asInt(), equalTo(0));
        }
    }
}