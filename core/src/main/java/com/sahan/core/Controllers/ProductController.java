package com.sahan.core.Controllers;

import com.sahan.core.Entities.Market.Product;
import com.sahan.core.Services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for handling product-related HTTP endpoints.
 * This class receives incoming HTTP requests and delegates the processing to the ProductService.
 */
@RestController
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    /**
     * Retrieves products associated with the given user ID.
     *
     * @param userId The user ID used for filtering products.
     * @return ResponseEntity with the list of products filtered by the user ID,
     *         or ResponseEntity with a bad request status if the userId is invalid,
     *         or ResponseEntity with a server error status if any unexpected exception occurs.
     */
//    @GetMapping("getproductbyuser")
//    public ResponseEntity<?> getProductByUser(@RequestParam Long userId) {
//        try {
//            // Validate the userId parameter
//            if (userId == null || userId <= 0) {
//                // If the userId is invalid, return a bad request response
//                return ResponseEntity.badRequest().body("Invalid userId parameter");
//            }
//
//            // Call the ProductService method to get products by user
//            List<Product> products = productService.getProductByUser(userId);
//            return ResponseEntity.ok(products);
//        } catch (Exception e) {
//            // Handle any unexpected exceptions and return an error response
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred while processing the request");
//        }
//    }

    /**
     * Retrieves products that belong to the specified market.
     *
     * @param market The market used for filtering products.
     * @return ResponseEntity with the list of products filtered by the market,
     *         or ResponseEntity with a bad request status if the market is empty or null,
     *         or ResponseEntity with a server error status if any unexpected exception occurs.
     */
    @GetMapping("getproductbymarket")
    public ResponseEntity<?> getProductByMarket(@RequestParam String market) {
        try {
            // Validate the market parameter
            if (market == null || market.isEmpty()) {
                // If the market parameter is empty or null, return a bad request response
                return ResponseEntity.badRequest().body("Market parameter cannot be empty");
            }

            // Call the ProductService method to get products by market
            List<Product> products = productService.getProductByMarket(market);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            // Handle any unexpected exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request");
        }
    }

    // Similar error handling and validation for other methods...

}
