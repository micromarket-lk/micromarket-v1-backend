package com.sahan.core.Exceptions.Market;

public class MarketNotFoundException extends RuntimeException {
    public MarketNotFoundException(String message) {
        super(message);
    }
}
