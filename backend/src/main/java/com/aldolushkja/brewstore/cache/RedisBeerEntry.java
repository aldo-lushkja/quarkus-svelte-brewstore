package com.aldolushkja.brewstore.cache;

import java.util.Date;

public class RedisBeerEntry {
    private final String data;
    private final Date expirationDate;

    public RedisBeerEntry(String data, Date expirationDate) {
        this.data = data;
        this.expirationDate = expirationDate;
    }

    public String getData() {
        return data;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}
