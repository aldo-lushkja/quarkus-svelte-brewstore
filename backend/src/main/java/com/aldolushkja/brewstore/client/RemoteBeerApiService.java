package com.aldolushkja.brewstore.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/breweries")
@RegisterRestClient(configKey = "punk-api")
public interface RemoteBeerApiService {

    @GET
    JsonNode getBeersByNameWithPagination();

    @GET
    @Path("/random")
    JsonNode getRandom();

    @GET
    JsonNode getBeersContainingFood(@QueryParam("name") String name);

}

