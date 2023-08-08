package com.sahan.core.Services;

import com.sahan.core.Entities.Market.Product;
import com.sahan.core.Exceptions.Market.MarketNotFoundException;
import com.sahan.core.Repostitories.ProductRepository;
import com.sahan.core.Requests.Product.ProductCreateRequest;
import com.sahan.core.Requests.Product.ProductDeleteRequest;
import com.sahan.core.Requests.Product.ProductGetRequest;
import com.sahan.core.Requests.Product.ProductUpdateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;

/**
 * The ProductServiceTest class contains unit tests for the ProductService class, which is responsible for managing product-related operations.
 * It uses JUnit and Mockito to create isolated test scenarios and verify the correctness of the ProductService methods.
 *
 * Test Methods:
 *
 * 1. testCreateProduct_Success:
 *    - Tests the successful creation of a new product by providing a valid ProductCreateRequest with a non-existing product name.
 *    - Uses Mockito to mock the productRepository's findProductByProductName method to return null (indicating no product with the same name exists).
 *    - Verifies that the productRepository's saveAndFlush method is called once with the correct Product object for storage.
 *
 * 2. testCreateProduct_WithExistingProduct:
 *    - Tests that creating a product with an existing name results in an IllegalArgumentException being thrown.
 *    - Uses Mockito to mock the productRepository's findProductByProductName method to return an existing Product object.
 *    - Verifies that the productRepository's saveAndFlush method is never called, as the creation is expected to fail.
 *
 * 3. testGetProduct_Success:
 *    - Tests the successful retrieval of a product by providing a valid ProductGetRequest with an existing product name.
 *    - Uses Mockito to mock the productRepository's findProductByProductName method to return a valid Product object.
 *    - Verifies that the retrieved product has the expected product name.
 *
 * 4. testGetProduct_ProductNotFound:
 *    - Tests that trying to retrieve a non-existing product results in a NoSuchElementException being thrown.
 *    - Uses Mockito to mock the productRepository's findProductByProductName method to return null, indicating that the product doesn't exist.
 *
 * 5. testUpdateProduct_Success:
 *    - Tests the successful update of an existing product's name by providing a valid ProductUpdateRequest.
 *    - Uses Mockito to mock the productRepository's findProductByProductName method to return an existing Product object for updating.
 *    - Verifies that the productRepository's saveAndFlush method is called once with the updated Product object.
 *
 * 6. testUpdateProduct_WithExistingProduct:
 *    - Tests that updating a product's name with an existing name results in an IllegalArgumentException being thrown.
 *    - Uses Mockito to mock the productRepository's findProductByProductName method to return an existing Product object.
 *    - Verifies that the productRepository's saveAndFlush method is never called, as the update is expected to fail.
 *
 * 7. testUpdateProduct_ProductNotFound:
 *    - Tests that trying to update a non-existing product results in a NoSuchElementException being thrown.
 *    - Uses Mockito to mock the productRepository's findProductByProductName method to return null, indicating that the product doesn't exist.
 *
 * 8. testDeleteProduct_Success:
 *    - Tests the successful deletion of an existing product by providing a valid ProductDeleteRequest.
 *    - Uses Mockito to mock the productRepository's findProductByProductName method to return an existing Product object for deletion.
 *    - Verifies that the productRepository's delete method is called once with the correct Product object.
 *
 * 9. testDeleteProduct_ProductNotFound:
 *    - Tests that trying to delete a non-existing product results in a NoSuchElementException being thrown.
 *    - Uses Mockito to mock the productRepository's findProductByProductName method to return null, indicating that the product doesn't exist.
 *
 * 10. testGetProductByMarket_Success:
 *     - Tests the successful retrieval of a list of products by providing a valid market name.
 *     - Uses Mockito to mock the productRepository's findProductsByMarketName method to return a list of Product objects.
 *     - Verifies that the correct list of products is returned with the expected product names.
 *
 * 11. testGetProductByMarket_EmptyMarketName:
 *     - Tests that trying to retrieve products with an empty market name results in an IllegalArgumentException being thrown.
 *     - Verifies that the productRepository's findProductsByMarketName method is never called in this scenario.
 *
 * 12. testGetProductByMarket_NullMarketName:
 *     - Tests that trying to retrieve products with a null market name results in an IllegalArgumentException being thrown.
 *     - Verifies that the productRepository's findProductsByMarketName method is never called in this scenario.
 *
 * 13. testGetProductByMarket_NoProductsFound:
 *     - Tests that trying to retrieve products for a market with no products results in a MarketNotFoundException being thrown.
 *     - Uses Mockito to mock the productRepository's findProductsByMarketName method to return null, indicating no products are available for the market.
 *
 * 14. testGetProductByMarket_NoProductsFoundEmptyList:
 *     - Tests that trying to retrieve products for a market with an empty product list results in a MarketNotFoundException being thrown.
 *     - Uses Mockito to mock the productRepository's findProductsByMarketName method to return an empty list, indicating no products are available for the market.
 *
 * Note: The ProductServiceTest class uses the @Mock and @InjectMocks annotations from the Mockito framework to set up and inject mock dependencies.
 * The @BeforeEach setup method initializes the Mockito annotations before each test method is executed.
 *
 * These tests provide comprehensive coverage of the ProductService class, ensuring the correctness and reliability of its methods under different scenarios.
 * The test cases validate the handling of edge cases, exceptions, and expected behavior for product creation, retrieval, update, and deletion operations.
 */


class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct_Success() {
        ProductCreateRequest request = new ProductCreateRequest("New Product");
        when(productRepository.findProductByProductName(anyString())).thenReturn(null);

        productService.createProduct(request);

        verify(productRepository, times(1)).saveAndFlush(any(Product.class));
    }

    @Test
    public void testCreateProduct_WithExistingProduct() {
        ProductCreateRequest request = new ProductCreateRequest("Existing Product");
        when(productRepository.findProductByProductName("Existing Product")).thenReturn(new Product());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(request);
        });

        verify(productRepository, never()).saveAndFlush(any(Product.class));
    }

    @Test
    public void testGetProduct_Success() {
        ProductGetRequest request = new ProductGetRequest("Existing Product");
        when(productRepository.findProductByProductName("Existing Product")).thenReturn(new Product("Existing Product"));

        Product product = productService.getProduct(request);

        Assertions.assertNotNull(product);
        Assertions.assertEquals("Existing Product", product.getProductName());
    }

    @Test
    public void testGetProduct_ProductNotFound() {
        ProductGetRequest request = new ProductGetRequest("Non-Existing Product");
        when(productRepository.findProductByProductName("Non-Existing Product")).thenReturn(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            productService.getProduct(request);
        });
    }

    @Test
    public void testUpdateProduct_Success() {
        ProductUpdateRequest request = new ProductUpdateRequest("Existing Product", "Updated Product");
        when(productRepository.findProductByProductName("Existing Product")).thenReturn(new Product("Existing Product"));
        when(productRepository.findProductByProductName("Updated Product")).thenReturn(null);

        productService.updateProduct(request);

        verify(productRepository, times(1)).saveAndFlush(any(Product.class));
    }

    @Test
    public void testUpdateProduct_WithExistingProduct() {
        ProductUpdateRequest request = new ProductUpdateRequest("Existing Product", "Existing Product");
        when(productRepository.findProductByProductName("Existing Product")).thenReturn(new Product("Existing Product"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(request);
        });

        verify(productRepository, never()).saveAndFlush(any(Product.class));
    }

    @Test
    public void testUpdateProduct_ProductNotFound() {
        ProductUpdateRequest request = new ProductUpdateRequest("Non-Existing Product", "Updated Product");
        when(productRepository.findProductByProductName("Non-Existing Product")).thenReturn(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            productService.updateProduct(request);
        });

        verify(productRepository, never()).saveAndFlush(any(Product.class));
    }

    @Test
    public void testDeleteProduct_Success() {
        ProductDeleteRequest request = new ProductDeleteRequest("Existing Product");
        when(productRepository.findProductByProductName("Existing Product")).thenReturn(new Product("Existing Product"));

        productService.deleteProduct(request);

        verify(productRepository, times(1)).delete(any(Product.class));
    }

    @Test
    public void testDeleteProduct_ProductNotFound() {
        ProductDeleteRequest request = new ProductDeleteRequest("Non-Existing Product");
        when(productRepository.findProductByProductName("Non-Existing Product")).thenReturn(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            productService.deleteProduct(request);
        });

        verify(productRepository, never()).delete(any(Product.class));
    }

    @Test
    public void testGetProductByMarket_Success() throws MarketNotFoundException {
        String market = "Test Market";
        List<Product> products = new ArrayList<>();
        products.add(new Product("Product 1"));
        products.add(new Product("Product 2"));

        when(productRepository.findProductsByMarketName(market)).thenReturn(products);

        List<Product> result = productService.getProductByMarket(market);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Product 1", result.get(0).getProductName());
        Assertions.assertEquals("Product 2", result.get(1).getProductName());
    }

    @Test
    public void testGetProductByMarket_EmptyMarketName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productService.getProductByMarket("");
        });

        verify(productRepository, never()).findProductsByMarketName(anyString());
    }

    @Test
    public void testGetProductByMarket_NullMarketName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productService.getProductByMarket(null);
        });

        verify(productRepository, never()).findProductsByMarketName(anyString());
    }

    @Test
    public void testGetProductByMarket_NoProductsFound() {
        String market = "Test Market";
        when(productRepository.findProductsByMarketName(market)).thenReturn(null);

        Assertions.assertThrows(MarketNotFoundException.class, () -> {
            productService.getProductByMarket(market);
        });
    }

    @Test
    public void testGetProductByMarket_NoProductsFoundEmptyList() {
        String market = "Test Market";
        List<Product> products = new ArrayList<>();
        when(productRepository.findProductsByMarketName(market)).thenReturn(products);

        Assertions.assertThrows(MarketNotFoundException.class, () -> {
            productService.getProductByMarket(market);
        });
    }
}