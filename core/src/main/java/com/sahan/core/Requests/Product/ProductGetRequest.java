package com.sahan.core.Requests.Product;

import lombok.Getter;
import lombok.Setter;

public record ProductGetRequest(String productName) {
    @Override
    public String productName() {
        return productName;
    }
}
