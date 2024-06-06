package com.aldolushkja.brewstore.controller;

import com.aldolushkja.brewstore.cache.CacheEntry;
import com.aldolushkja.brewstore.cache.RedisService;
import com.aldolushkja.brewstore.event.CacheEvent;
import com.aldolushkja.brewstore.service.BeerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jboss.logging.Logger;

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
import java.util.Date;

@Path("/beers")
public class BeersController {

    @Inject
    Logger logger;

    @Inject
    BeerService beerService;

    @Inject
    RedisService redisService;

    @Inject
    Event<CacheEvent> cacheEvents;

    @GET
    @Path("/search-by-name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBeer(@PathParam ("name") String name,
                            @QueryParam("page") int page,
                            @QueryParam("per_page") int rows) throws JsonProcessingException {
        return Response.ok(beerService.getBeersByName(name,page, rows)).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBeer(@QueryParam("page") int page, @QueryParam("per_page") int rows) throws JsonProcessingException {
        return Response.ok(beerService.fetchIpaBeers(page, rows)).build();
    }

    @GET
    @Path("/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandom() throws JsonProcessingException {
        return Response.ok(beerService.getRandom()).build();
    }


    @Path("/test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response testApi() throws JsonProcessingException {
        final var beers = beerService.fetchIpaBeers(1,10);
        var start = System.currentTimeMillis();
        CacheEntry cacheEntry = null;
        try {
            cacheEntry = redisService.getItem("test");
        } catch (JsonProcessingException e) {
            logger.error("Error while fetching key from redis: " + e.getMessage());
        }
        if(cacheEntry != null && new Date().before(cacheEntry.getExpirationDate())){
            logger.info("Cache entry found in cache");
            cacheEvents.fireAsync(new CacheEvent("REDIS","HIT", LocalDateTime.now(),"OK"));
            logger.info("Fired cache event.");
            var end = System.currentTimeMillis();
            logger.info("Time taken to fetch from cache: " + (end - start) + "ms");
            return Response.ok(cacheEntry.getData()).build();
        }
        addToRedis(beers);
        var end = System.currentTimeMillis();
        logger.info("Time taken to fetch from api: " + (end - start) + "ms");
        return Response.ok(beers).build();
    }

    private void addToRedis(String beers) throws JsonProcessingException {
        // add 2 minutes to the current time
        final var expirationDate = new Date(System.currentTimeMillis() + 120000);
        logger.info("Adding key to redis with expiration date: " + expirationDate);
        CacheEntry cacheEntry = new CacheEntry(beers, expirationDate);
        redisService.addItem("test", cacheEntry);
        logger.info("Added key to redis.");
        cacheEvents.fireAsync(new CacheEvent("REDIS","INSERT", LocalDateTime.now(),"OK"));
        logger.info("Fired cache event.");
    }


}
