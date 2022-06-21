package com.aldolushkja.brewstore.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.json.JsonArray;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/beers")
@RegisterRestClient(configKey = "punk-api")
public interface PunkApiService {

    @GET
    JsonArray getBeersByNameWithPagination(
            @QueryParam(value = "beer_name") @DefaultValue("ipa") String name,
            @QueryParam(value = "page") @DefaultValue("10") int page,
            @QueryParam("per_page") @DefaultValue("10") int perPage);

    @GET
    @Path("/random")
    JsonArray getRandom();

    @GET
    JsonArray getBeersContainingFood(@QueryParam("name") String name);

}

