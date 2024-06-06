package com.aldolushkja.brewstore.client;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class PunkApiServiceTest {

    @Inject
    @RestClient
    PunkApiService punkApiService;

    @Test
    void getBeersWithNameAndPageAndPerPageFilters() {
        final var beers = punkApiService.getBeersByNameWithPagination();
        System.out.println(beers);
        assertNotNull(beers);
    }

    @Test
    void getRandom() {
        final var beers = punkApiService.getRandom();
        System.out.println(beers);
        assertNotNull(beers);
    }

    @Test
    void getBeersContainingFood() {
        final var beers = punkApiService.getBeersContainingFood("meat");
        System.out.println(beers);
        assertNotNull(beers);
    }

}