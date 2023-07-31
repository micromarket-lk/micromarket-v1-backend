package com.sahan.core.Repostitories;

import com.sahan.core.Entities.Market.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findProductByProductName(String productName);
}
