package com.aldolushkja.brewstore.brewery;

import com.aldolushkja.brewstore.service.BeerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jboss.resteasy.annotations.Body;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/breweries")
public class BreweryResource {

    private final BeerService beerService;

    public BreweryResource(BeerService beerService) {
        this.beerService = beerService;
    }

    @POST
    @Path("search")
    @Consumes("application/json")
    public String getBreweries(SearchBreweryRequest request) {
        String response = "";
        try {
            response = beerService.getBeersByName(request.getName(), request.getPage(), request.getPerPage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

}
