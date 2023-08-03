package com.sahan.core.Requests.Market;

import lombok.Getter;
import lombok.Setter;


public record MarketDeleteRequest(String marketName) {
    @Override
    public String marketName() {
        return marketName;
    }
}

