package com.sahan.fraud;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * The FraudCheckService class is a Spring Service responsible for fraud check operations.
 *
 * This class is annotated with @Service, indicating that it is a service component managed by Spring.
 * The @AllArgsConstructor annotation generates a constructor with all the required dependencies.
 * The dependencies are then injected through this constructor.
 * The @Slf4j annotation is used to enable logging capabilities with the SLF4J (Simple Logging Facade for Java) library.
 * The logger variable 'log' will be automatically initialized by Lombok.
 */
@Service
@AllArgsConstructor
@Slf4j
public class FraudCheckService {

    /**
     * The FraudCheckHistoryRepository dependency is injected via the constructor using the @AllArgsConstructor annotation.
     * This ensures that an instance of FraudCheckHistoryRepository is available to this class when it's created.
     */
    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    /**
     * Method to check if a customer is fraudulent based on their ID.
     * This method saves the fraud check history into the FraudCheckHistoryRepository.
     *
     * @param customerId The ID of the customer to check for fraud.
     * @return true if the customer is flagged as fraudulent, false otherwise.
     */
    public boolean isFraudulentCustomer(Integer customerId) {
        // Save the fraud check history for the customer into the repository
        fraudCheckHistoryRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(false) // Assuming the customer is not flagged as a fraudster in this example
                        .createdAt(LocalDate.now())
                        .build()
        );

        // Log that the fraud service was hit for the given customer ID
        log.info("hit fraud service for customer {}", customerId);

        // Return false assuming the customer is not fraudulent in this example
        return false;
    }
}




