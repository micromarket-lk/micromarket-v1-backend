package com.sahan.core.Services;

import com.sahan.core.Entities.Market.Product;
import com.sahan.core.Exceptions.Market.MarketNotFoundException;
import com.sahan.core.Repostitories.ProductRepository;
import com.sahan.core.Requests.Product.ProductCreateRequest;
import com.sahan.core.Requests.Product.ProductDeleteRequest;
import com.sahan.core.Requests.Product.ProductGetRequest;
import com.sahan.core.Requests.Product.ProductUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
@Slf4j
public class ProductService {

    // The ProductRepository is injected as a dependency using constructor injection.
    private final ProductRepository productRepository;

    /**
     * Creates a new product based on the provided create request.
     *
     * @param productCreateRequest The request object containing the product name for creation.
     * @throws IllegalArgumentException If the provided product name is empty or null, or if a product with the same name already exists.
     */
    public void createProduct(@Valid ProductCreateRequest productCreateRequest) {
        // Validate the request to ensure the product name is not empty or null.
        String productName = productCreateRequest.productName();
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty or null.");
        }

        // Check if a product with the same name already exists.
        if (productRepository.findProductByProductName(productName) != null) {
            throw new IllegalArgumentException("A product with the same name already exists.");
        }

        // Create a new Product object with the provided product name.
        //Product product = new Product(productName);
        Product product = Product.builder().productName(productName).build();

        // Save the new product to the database.
        productRepository.saveAndFlush(product);
    }

    /**
     * Retrieves a product based on the provided get request.
     *
     * @param productGetRequest The request object containing the product name for retrieval.
     * @return The Product object matching the provided product name.
     * @throws IllegalArgumentException If the provided product name is empty or null.
     * @throws NoSuchElementException If a product with the specified name does not exist in the database.
     */
    public Product getProduct(@Valid ProductGetRequest productGetRequest) {
        // Validate the request to ensure the product name is not empty or null.
        String productName = productGetRequest.productName();
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty or null.");
        }

        // Find the product with the specified product name in the database.
        Product product = productRepository.findProductByProductName(productName);

        // Check if the product was found in the database.
        if (product == null) {
            throw new NoSuchElementException("Product not found with the specified name.");
        }

        // Log the retrieved product information.
        log.info(product.toString());

        // Return the retrieved product.
        return product;
    }

    /**
     * Updates an existing product's name based on the provided update request.
     *
     * @param productUpdateRequest The request object containing the old and new product names.
     * @throws IllegalArgumentException If the provided old or new product names are empty or null,
     *                                  or if a product with the new name already exists.
     * @throws NoSuchElementException If a product with the specified old name does not exist in the database.
     */
    public void updateProduct(@Valid ProductUpdateRequest productUpdateRequest) {
        // Validate the request to ensure the product names are not empty or null.
        String oldProductName = productUpdateRequest.oldProductName();
        String newProductName = productUpdateRequest.newProductName();

        if (oldProductName == null || oldProductName.isEmpty() ||
                newProductName == null || newProductName.isEmpty()) {
            throw new IllegalArgumentException("Product names cannot be empty or null.");
        }

        // Find the product with the specified old product name in the database.
        Product product = productRepository.findProductByProductName(oldProductName);

        // Check if the product was found in the database.
        if (product == null) {
            throw new NoSuchElementException("Product not found with the specified name.");
        }

        // Check if the new product name already exists in the database (excluding the current product).
        if (!oldProductName.equals(newProductName) && productRepository.findProductByProductName(newProductName) != null) {
            throw new IllegalArgumentException("A product with the new name already exists.");
        }

        // Update the product name with the new value from the update request.
        product.setProductName(newProductName);
        // Save the updated product to the database.
        productRepository.saveAndFlush(product);
    }

    /**
     * Deletes a product with the specified product name.
     *
     * @param productDeleteRequest The request object containing the product name to be deleted.
     * @throws IllegalArgumentException If the provided product name is empty or null.
     * @throws NoSuchElementException If a product with the specified name does not exist in the database.
     */
    public void deleteProduct(@Valid ProductDeleteRequest productDeleteRequest) {
        // Validate the request to ensure the product name is not empty or null.
        String productName = productDeleteRequest.productName();


        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty or null.");
        }

        // Find the product with the specified product name in the database.
        Product product = productRepository.findProductByProductName(productName);

        // Check if the product was found in the database.
        if (product == null) {
            throw new NoSuchElementException("Product not found with the specified name.");
        }

        // Delete the product with the specified product name from the database.
        productRepository.delete(product);
    }

    /**
     * Retrieves a list of products by the specified market name.
     *
     * @param market The name of the market to search for products.
     * @return A list of products found for the specified market.
     * @throws IllegalArgumentException If the market name is empty or null.
     * @throws MarketNotFoundException If no products are found for the specified market.
     */
    public List<Product> getProductByMarket(String market) throws IllegalArgumentException, MarketNotFoundException {
        // Validate the market name to ensure it is not empty or null
        if (market == null || market.trim().isEmpty()) {
            throw new IllegalArgumentException("Market name cannot be empty or null.");
        }

        // Call the productRepository to find products by market name
        List<Product> products = productRepository.findProductsByMarketName(market);

        // Check if products is null (in case the repository returned null instead of an empty list)
        if (products == null) {
            throw new MarketNotFoundException("Products not available for market: " + market);
        }

        // Handle the case when no products are found for the market
        if (products.isEmpty()) {
            throw new MarketNotFoundException("No products found for market: " + market);
        }

        // Return the list of found products
        return products;
    }

}
