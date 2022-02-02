package com.aldolushkja.brewstore.infinispan;

import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

public class BeerSchema {

    private final String imageUrl;
    private final String name;
    private final String description;

    @ProtoFactory
    public BeerSchema(String imageUrl, String name, String description) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
    }

    @ProtoField(number = 1)
    public String getImageUrl() {
        return imageUrl;
    }

    @ProtoField(number = 2)
    public String getName() {
        return name;
    }

    @ProtoField(number = 3)
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BeerSchema{");
        sb.append("imageUrl='").append(imageUrl).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
