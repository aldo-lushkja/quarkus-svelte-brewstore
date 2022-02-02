package com.aldolushkja.brewstore.beers;

import com.aldolushkja.brewstore.client.PunkApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logmanager.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;

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

    public String fetchData(int page, int perPage) {
        var response = punkApiService.getBeers(page, perPage);
        if (response != null) {
            return builder.toJson(response);
        } else {
            return "";
        }
    }
}
