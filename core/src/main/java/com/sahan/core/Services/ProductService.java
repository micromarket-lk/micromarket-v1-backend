package com.sahan.core.Services;

import com.sahan.core.Entities.Market.Product;
import com.sahan.core.Repostitories.ProductRepository;
import com.sahan.core.Requests.Product.ProductCreateRequest;
import com.sahan.core.Requests.Product.ProductDeleteRequest;
import com.sahan.core.Requests.Product.ProductGetRequest;
import com.sahan.core.Requests.Product.ProductUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    // create
    public void createProduct(ProductCreateRequest productCreateRequest) {
        Product product = Product.builder().productName(productCreateRequest.productName()).build();
        productRepository.saveAndFlush(product);
    }

    //getProduct
    public void getProduct(ProductGetRequest productGetRequest) {
        Product product = productRepository.findProductByProductName(productGetRequest.productName());
        log.info(product.toString());
    }

    //updateProduct
    public void updateProduct(ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findProductByProductName(productUpdateRequest.productName());
        product.setProductName(productUpdateRequest.productName());
    }

    //deleteProduct
    public void deleteProduct(ProductDeleteRequest productDeleteRequest) {
        Product product = productRepository.findProductByProductName(productDeleteRequest.productName());
        productRepository.delete(product);
    }
}
