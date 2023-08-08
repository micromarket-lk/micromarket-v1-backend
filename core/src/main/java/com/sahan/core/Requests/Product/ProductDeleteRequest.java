package com.sahan.core.Requests.Product;

import lombok.Getter;
import lombok.Setter;

public record ProductDeleteRequest(String productName) {
    @Override
    public String productName() {
        return productName;
    }
}
