package com.aldolushkja.brewstore.client;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class RemoteBeerApiServiceTest {

    @Inject
    @RestClient
    RemoteBeerApiService remoteBeerApiService;

    @Test
    void getBeersWithNameAndPageAndPerPageFilters() {
        final var beers = remoteBeerApiService.getBeersByNameWithPagination();
        System.out.println(beers);
        assertNotNull(beers);
    }

    @Test
    void getRandom() {
        final var beers = remoteBeerApiService.getRandom();
        System.out.println(beers);
        assertNotNull(beers);
    }

    @Test
    void getBeersContainingFood() {
        final var beers = remoteBeerApiService.getBeersContainingFood("meat");
        System.out.println(beers);
        assertNotNull(beers);
    }

}