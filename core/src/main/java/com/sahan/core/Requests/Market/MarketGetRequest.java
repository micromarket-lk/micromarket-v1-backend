package com.sahan.core.Requests.Market;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public record MarketGetRequest(String marketName) {
}
