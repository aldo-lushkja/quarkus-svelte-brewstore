package com.aldolushkja.brewstore.greets;

import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/greets")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response jsonExample() {
        return Response.ok(Json.createObjectBuilder().add("message", "Hello from Quarkus")
                .add("timestamp", System.currentTimeMillis()).build()).build();
    }
}