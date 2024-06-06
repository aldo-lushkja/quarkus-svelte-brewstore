package com.aldolushkja.brewstore.service;

import com.aldolushkja.brewstore.client.RemoteBeerApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BeerService {

    @Inject
    @RestClient
    RemoteBeerApiService remoteBeerApiService;

    @Inject
    Logger logger;

    @Inject
    ObjectMapper objectMapper;

    public String getBeersByName(String name, int page, int perPage) throws JsonProcessingException {
        logger.info("Fetching beers from punk api");
        var response = remoteBeerApiService.getBeersByNameWithPagination();
        if (response != null) {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        } else {
            return "";
        }
    }
    public String fetchIpaBeers(int page, int perPage) throws JsonProcessingException {
        logger.info("Fetching beers from punk api");
        var response = remoteBeerApiService.getBeersByNameWithPagination();
        if (response != null) {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        } else {
            return "";
        }
    }

    public String getRandom() throws JsonProcessingException {
        final var random = remoteBeerApiService.getRandom();
        if(random != null){
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(random);
        } else {
            return "";
        }
    }
}
