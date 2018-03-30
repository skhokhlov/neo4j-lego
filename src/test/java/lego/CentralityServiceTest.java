package lego;

import org.junit.Rule;
import org.junit.Test;
import org.neo4j.harness.junit.Neo4jRule;
import org.neo4j.kernel.configuration.Settings;
import org.neo4j.kernel.configuration.ssl.LegacySslPolicyConfig;
import org.neo4j.server.configuration.ServerSettings;
import org.neo4j.test.server.HTTP;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.neo4j.server.ServerTestUtils.getRelativePath;
import static org.neo4j.server.ServerTestUtils.getSharedTestTemporaryFolder;


public class CentralityServiceTest {
    final private Example example = new Example();

    @Rule
    public Neo4jRule neo4j = new Neo4jRule().withFixture(example.getGraphStatement())
            .withConfig(LegacySslPolicyConfig.certificates_directory.name(),
                    getRelativePath(getSharedTestTemporaryFolder(), LegacySslPolicyConfig.certificates_directory))
            .withConfig( ServerSettings.script_sandboxing_enabled.name(), Settings.TRUE )
            .withExtension("/centrality/helloworld2/", CentralityService.class)
            .withExtension("/centrality/closeness", CentralityService.class);

    @Test
    public void shouldWorkWithServer() throws IOException {
        HTTP.Response response = HTTP.GET(neo4j.httpURI().toString());
        assertEquals(200, response.status());
    }

    @Test
    public void shouldRetrieveCentralityService() throws IOException {
        HTTP.Response response = HTTP.GET(neo4j.httpsURI().resolve(
                "/centrality/helloworld2/"
        ).toString());

        assertEquals("0", response.content());
        assertEquals(200, response.status());
    }
}
