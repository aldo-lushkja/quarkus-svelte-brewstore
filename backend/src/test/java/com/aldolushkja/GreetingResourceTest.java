package com.aldolushkja;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {


    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/greets")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy1234"));
    }

    
    @Test
    public void testInsertCache() {
        given()
          .when().get("/beers/insert-cache")
          .then()
             .statusCode(200);
    }
    
    @Test
    public void testInsertCacheBulkWith10() {
        given()
          .when().get("/beers/insert-cache-bulk/10")
          .then()
             .statusCode(200);
    }
    
    @Test
    public void testInsertCacheBulkWith1000() {
        given()
          .when().get("/beers/insert-cache-bulk/1000")
          .then()
             .statusCode(200);
    }
    @Test
    public void testInsertCacheBulkWith10000() {
    	given()
    	.when().get("/beers/insert-cache-bulk/10000")
    	.then()
    	.statusCode(200);
    }

}