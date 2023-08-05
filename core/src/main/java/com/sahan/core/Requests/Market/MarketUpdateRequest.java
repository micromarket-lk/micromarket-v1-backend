package com.sahan.core.Requests.Market;

import lombok.Getter;
import lombok.Setter;

public record MarketUpdateRequest(String oldMarketName, String newMarketName) {
    @Override
    public String oldMarketName() {
        return oldMarketName;
    }

    @Override
    public String newMarketName() {
        return newMarketName;
    }
}
