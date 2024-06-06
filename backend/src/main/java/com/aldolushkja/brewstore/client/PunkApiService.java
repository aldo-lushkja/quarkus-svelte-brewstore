package com.aldolushkja.brewstore.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.json.JsonArray;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/breweries")
@RegisterRestClient(configKey = "punk-api")
public interface PunkApiService {

    @GET
    JsonArray getBeersByNameWithPagination();

    @GET
    @Path("/random")
    JsonArray getRandom();

    @GET
    JsonArray getBeersContainingFood(@QueryParam("name") String name);

}

