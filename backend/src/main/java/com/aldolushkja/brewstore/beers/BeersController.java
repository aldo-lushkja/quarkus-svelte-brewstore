package com.aldolushkja.brewstore.beers;

import com.aldolushkja.brewstore.infinispan.BeerCacheEntry;
import com.aldolushkja.brewstore.redis.RedisService;
import io.quarkus.infinispan.client.Remote;
import org.infinispan.client.hotrod.RemoteCache;
import org.jboss.logmanager.Logger;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;

@Path("/beers")
public class BeersController {

    Logger log = Logger.getLogger(String.valueOf(BeersController.class));

    @Inject
    BeerService beerService;

    @Inject
    RedisService redisService;

    @Inject
    @Remote("beers-punk-api")
    RemoteCache<String, BeerCacheEntry> cache;

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBeer(@QueryParam("page") int page, @QueryParam("per_page") int rows) {
        final var json = beerService.fetchData(page, rows);
        return Response.ok(json).build();
    }


    @Path("/test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response testApi() {
        final var beers = beerService.fetchData(1,10);
        redisService.addItem("test", beers);
        return Response.ok(beers).build();
    }


}
