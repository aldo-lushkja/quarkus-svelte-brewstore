package com.aldolushkja.brewstore.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.redis.client.RedisClient;
import io.vertx.redis.client.Response;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class RedisService {

    @Inject
    RedisClient redisClient;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    Logger logger;

    public void addItem(String key, RedisBeerEntry redisBeerEntry) throws JsonProcessingException {
        redisClient.set(Arrays.asList(key,objectMapper.writeValueAsString(redisBeerEntry)));
    }

    public RedisBeerEntry getItem(String key) throws JsonProcessingException {
        Response response = redisClient.get(key);
        if(response == null) {
            logger.warn("Key not found in cache");
            return null;
        }
        return objectMapper.readValue(response.toString(), RedisBeerEntry.class);
    }
}
