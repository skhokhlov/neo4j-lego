package lego;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.logging.Log;
import org.neo4j.string.UTF8;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;

@Path("/centrality23")
public class CentralityService {
    private final GraphDatabaseService database;

    public CentralityService( @Context GraphDatabaseService database )
    {
        this.database = database;
    }

    @GET
    @Path("/helloworld")
    public String helloWorld() {
        return "Hello World!";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/helloworld2")
    public Response helloworld2(@Context GraphDatabaseService db, @Context Log log) throws IOException {
        log.info("Hello world");
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
