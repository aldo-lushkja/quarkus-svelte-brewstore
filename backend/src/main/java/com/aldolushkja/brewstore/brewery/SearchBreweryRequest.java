package com.aldolushkja.brewstore.brewery;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchBreweryRequest {

    private String name;
    private Integer page;
    @JsonProperty("per_page")
    private Integer perPage;

    public SearchBreweryRequest() {
    }

    public SearchBreweryRequest(String name, Integer page, Integer perPage) {
        this.name = name;
        this.page = page;
        this.perPage = perPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }
}
