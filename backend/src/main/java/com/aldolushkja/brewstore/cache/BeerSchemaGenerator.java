package com.aldolushkja.brewstore.cache;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;


@AutoProtoSchemaBuilder(includeClasses = {BeerSchema.class, BeerCacheEntry.class})
public interface BeerSchemaGenerator extends GeneratedSchema {
}
