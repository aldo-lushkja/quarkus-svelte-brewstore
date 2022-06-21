package com.aldolushkja.brewstore.controller;

import com.aldolushkja.brewstore.event.CacheEvent;
import com.aldolushkja.brewstore.cache.BeerCacheEntry;
import com.aldolushkja.brewstore.cache.BeerSchema;
import com.aldolushkja.brewstore.cache.RedisService;
import com.aldolushkja.brewstore.service.BeerService;
import io.quarkus.infinispan.client.Remote;
import org.infinispan.client.hotrod.RemoteCache;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.HashSet;

@Path("/beers")
public class BeersController {

    @Inject
    BeerService beerService;

    @Inject
    RedisService redisService;

    @Inject
    @Remote("beers-punk-api")
    RemoteCache<String, BeerCacheEntry> cache;

    @Inject
    Event<CacheEvent> cacheEvents;

    @GET
    @Path("/search-by-name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBeer(@PathParam ("name") String name,
                            @QueryParam("page") int page,
                            @QueryParam("per_page") int rows) {
        return Response.ok(beerService.getBeersByName(name,page, rows)).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBeer(@QueryParam("page") int page, @QueryParam("per_page") int rows) {
        return Response.ok(beerService.fetchIpaBeers(page, rows)).build();
    }

    @GET
    @Path("/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandom() {
        return Response.ok(beerService.getRandom()).build();
    }


    @Path("/test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response testApi() {
        final var beers = beerService.fetchIpaBeers(1,10);
        addToRedis(beers);
        addToInfinispanCache();
        return Response.ok(beers).build();
    }

    private void addToRedis(String beers) {
        redisService.addItem("test", beers);
        cacheEvents.fireAsync(new CacheEvent("REDIS","INSERT", LocalDateTime.now(),"OK"));
    }

    private void addToInfinispanCache() {
        final var objects = new HashSet<BeerSchema>();
        objects.add(new BeerSchema("","",""));
        cache.put("test", new BeerCacheEntry(objects));
        cacheEvents.fireAsync(new CacheEvent("ISPN","INSERT", LocalDateTime.now(),"OK"));
    }


}
