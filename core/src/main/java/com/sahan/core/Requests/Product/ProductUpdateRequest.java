package com.sahan.core.Requests.Product;

import lombok.Getter;
import lombok.Setter;

public record ProductUpdateRequest(String oldProductName, String newProductName) {
    @Override
    public String oldProductName() {
        return oldProductName;
    }

    @Override
    public String newProductName() {
        return newProductName;
    }
}
