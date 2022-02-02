package com.aldolushkja.brewstore.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.json.JsonArray;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/beers")
@RegisterRestClient(configKey = "punk-api")
public interface PunkApiService {

    @GET
    JsonArray getBeers(@QueryParam(value = "page") int page, @QueryParam("per_page") int perPage);

}

