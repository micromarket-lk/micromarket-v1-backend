package com.sahan.core.Requests.Market;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record MarketUpdateRequest(String marketName) {
}
