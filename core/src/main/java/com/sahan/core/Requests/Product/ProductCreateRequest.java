package com.sahan.core.Requests.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record ProductCreateRequest(String productName) {
}
