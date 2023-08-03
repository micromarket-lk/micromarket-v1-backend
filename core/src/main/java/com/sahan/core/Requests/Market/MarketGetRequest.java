package com.sahan.core.Requests.Market;

import lombok.Getter;
import lombok.Setter;


public record MarketGetRequest(String marketName) {
    @Override
    public String marketName() {
        return marketName;
    }
}
