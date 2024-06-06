package com.aldolushkja.brewstore.event;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class CacheEventObserver {

    @Inject
    Logger logger;

    public void observeIncomingCountEvent(@Observes CacheEvent cacheEvent) {
        logger.info("Received event: "+ cacheEvent);
    }
}
