package lego;

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
    private final GraphDatabaseService db;

    public CentralityService(@Context GraphDatabaseService db) {
        this.db = db;
    }

    @GET
    @Path("/helloworld")
    public String helloWorld() {
        return "Hello World!";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/helloworld2/")
    public Response helloworld2() {
        return Response.ok().entity(UTF8.encode("Str")).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/closeness/{label}/{nodeId}")
    public Response closeness(@PathParam("label") String label, @PathParam("nodeId") long nodeId) {
//        Graph graph = new GraphLoader(db, label).load();
        // Do stuff with the database
        return Response
                .status(Status.OK)
//                .entity(UTF8.encode("nodeId=" + new Closeness().getVertexScore(graph, nodeId)))
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/test/{nodeId}")
    public Response hello(@PathParam("nodeId") long nodeId) {
        return Response.status(Status.OK).entity(UTF8.encode("Hello World, nodeId=" + nodeId)).build();
    }
}
