package com.aldolushkja.brewstore.event;


import java.time.LocalDateTime;

public class CacheEvent {
    private final String name;
    private final String action;
    private final LocalDateTime executedAt;
    private final String status;

    public CacheEvent(String name, String action, LocalDateTime executedAt, String status) {
        this.name = name;
        this.action = action;
        this.executedAt = executedAt;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public String getStatus() {
        return status;
    }
}
