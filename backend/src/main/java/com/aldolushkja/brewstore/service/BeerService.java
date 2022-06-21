package com.aldolushkja.brewstore.service;

import com.aldolushkja.brewstore.client.PunkApiService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class BeerService {

    @Inject
    @RestClient
    PunkApiService punkApiService;

    Jsonb builder;

    @PostConstruct
    public void init() {
        builder = JsonbBuilder.newBuilder().build();
    }

    public String getBeersByName(String name, int page, int perPage) {
        var response = punkApiService.getBeersByNameWithPagination(name,page,perPage);
        if (response != null) {
            return builder.toJson(response);
        } else {
            return "";
        }
    }
    public String fetchIpaBeers(int page, int perPage) {
        var response = punkApiService.getBeersByNameWithPagination("ipa",page, perPage);
        if (response != null) {
            return builder.toJson(response);
        } else {
            return "";
        }
    }

    public String getRandom(){
        final var random = punkApiService.getRandom();
        if(random != null){
            return builder.toJson(random);
        } else {
            return "";
        }
    }
}
