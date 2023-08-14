package com.sahan.fraud;

import com.sahan.clients.fraud.FraudCheckResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The FraudController class is a Spring RestController responsible for handling incoming HTTP requests
 * related to fraud checks.
 *
 * This class is annotated with @RestController, indicating that it is a controller that handles RESTful API requests.
 * The base request mapping for all endpoints in this controller is "/api/v1/fraud-check".
 * The @AllArgsConstructor annotation generates a constructor with all the required dependencies.
 * The dependencies are then injected through this constructor.
 * The @Slf4j annotation is used to enable logging capabilities with the SLF4J (Simple Logging Facade for Java) library.
 * The logger variable 'log' will be automatically initialized by Lombok.
 */
@RestController
@RequestMapping("api/v1/fraud-check")
@AllArgsConstructor
@Slf4j
public class FraudController {

    /**
     * The FraudCheckService dependency is injected via the constructor using the @AllArgsConstructor annotation.
     * This ensures that an instance of FraudCheckService is available to this class when it's created.
     */
    public final FraudCheckService fraudCheckService;

    /**
     * HTTP GET endpoint for fraud check.
     * This method is responsible for processing incoming GET requests to "/api/v1/fraud-check/{customerId}".
     * The {customerId} path variable represents the ID of the customer to check for fraud.
     *
     * @param customerId The ID of the customer to check for fraud.
     * @return A FraudCheckResponse object containing the result of the fraud check (true if fraudulent, false otherwise).
     */
    @GetMapping(path = "{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId) {
        // Perform fraud check using the FraudCheckService
        boolean isFraudulentCustomer = fraudCheckService.isFraudulentCustomer(customerId);

        // Log the fraud check request for the given customer ID
        log.info("fraud check request for customer {}", customerId);

        // Return the FraudCheckResponse containing the result of the fraud check
        return new FraudCheckResponse(isFraudulentCustomer);
    }
}
