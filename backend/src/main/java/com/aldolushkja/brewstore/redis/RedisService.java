package com.aldolushkja.brewstore.redis;

import io.quarkus.redis.client.RedisClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class RedisService {

    @Inject
    RedisClient redisClient;

    public void addItem(String key, String json){
        redisClient.set(Arrays.asList(key,json));
    }

    public String getItem(String key){
        return redisClient.get(key).toString();
    }
}
