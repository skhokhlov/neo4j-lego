package lego;

import lego.Algorithms.Closeness;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.string.UTF8;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/centrality")
public class CentralityService {
    @Context
    private GraphDatabaseService db;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/closenness/{label}/{nodeId}")
    public Response hello(@PathParam("label") String label, @PathParam("nodeId") long nodeId) {
        Graph graph = new GraphLoader(db, label).load();
        // Do stuff with the database
        return Response
                .status(Status.OK)
                .entity(UTF8.encode("Hello World, nodeId=" + new Closeness().getVertexScore(graph, nodeId)))
                .build();
    }
}
