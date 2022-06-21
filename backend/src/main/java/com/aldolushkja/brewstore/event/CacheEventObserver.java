package com.aldolushkja.brewstore.event;

import javax.enterprise.event.Observes;
import java.util.logging.Logger;

public class CacheEventObserver {

    Logger logger = Logger.getLogger(CacheEventObserver.class.getName());

    public void observeIncomingCountEvent(@Observes CacheEvent cacheEvent) {
        System.out.println("Received event: "+ cacheEvent);
    }
}
