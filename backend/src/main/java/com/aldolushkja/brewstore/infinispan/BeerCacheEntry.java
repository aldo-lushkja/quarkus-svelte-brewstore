package com.aldolushkja.brewstore.infinispan;

import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

import java.util.Set;

public class BeerCacheEntry {

    private final Set<BeerSchema> beers;

    @ProtoFactory
    public BeerCacheEntry(Set<BeerSchema> beers) {
        this.beers = beers;
    }

    @ProtoField(number = 1)
    public Set<BeerSchema> getBeers() {
        return beers;
    }
}
