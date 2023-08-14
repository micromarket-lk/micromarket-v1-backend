package com.sahan.core.Controllers;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.sahan.core.Entities.Market.Product;
import com.sahan.core.Services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class ProductControllerTests {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductByMarket_ValidMarket_ShouldReturnProducts() {
        // Arrange
        String validMarket = "Test Market";
        List<Product> mockProducts = new ArrayList<>();

        mockProducts.add(new Product(new String("1"), "Product 1", validMarket));
        mockProducts.add(new Product(new String("2"), "Product 2", validMarket));
        when(productService.getProductByMarket(validMarket)).thenReturn(mockProducts);

        // Act
        ResponseEntity<?> response = productController.getProductByMarket(validMarket);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<Product> products = (List<Product>) response.getBody();
        assertEquals(2, products.size());
        assertEquals("Product 1", products.get(0).getProductName());
        assertEquals("Product 2", products.get(1).getProductName());
    }

    @Test
    public void testGetProductByMarket_EmptyMarket_ShouldReturnBadRequest() {
        // Arrange
        String emptyMarket = "";

        // Act
        ResponseEntity<?> response = productController.getProductByMarket(emptyMarket);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Market parameter cannot be empty", response.getBody());
    }

    @Test
    public void testGetProductByMarket_NullMarket_ShouldReturnBadRequest() {
        // Arrange
        String nullMarket = null;

        // Act
        ResponseEntity<?> response = productController.getProductByMarket(nullMarket);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Market parameter cannot be empty", response.getBody());
    }
}
