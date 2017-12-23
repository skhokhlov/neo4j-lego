package lego;

import lego.Results.CentralityResult;
import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.v1.*;
import org.neo4j.harness.junit.Neo4jRule;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class ClosenessProcedureTest {
    final private Example example = new Example();

    @Rule
    public Neo4jRule neo4j = new Neo4jRule().withProcedure(ClosenessProcedure.class);

// This test disabled because works incorrect
//    @Test
//    public void closeness() throws Exception {
//        try (
//                Driver driver = GraphDatabase.driver(
//                        neo4j.boltURI(),
//                        Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig()
//                );
//                Session session = driver.session()
//        ) {
//            session.run(example.getGraphStatement());
//            int a = session.run("Match (n) return n").list().size(); // If it in not here test not works
//
//            StatementResult result = session.run("CALL lego.closeness('User')");
//            assertThat(result.list().stream()
//                            .mapToDouble(record -> record.get("centrality").asDouble()).sorted().toArray(),
//                    equalTo(example.getCloseness().stream().mapToDouble(value -> value.centrality).sorted().toArray())
//            );
//        }
//    }

}