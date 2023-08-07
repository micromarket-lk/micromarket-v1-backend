package com.sahan.core.Services;

import com.sahan.core.Entities.Market.Market;
import com.sahan.core.Repostitories.MarketRepository;
import com.sahan.core.Requests.Market.MarketDeleteRequest;
import com.sahan.core.Requests.Market.MarketGetRequest;
import com.sahan.core.Requests.Market.MarketRegistrationRequest;
import com.sahan.core.Requests.Market.MarketUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class MarketService {

    // The MarketRepository is injected as a dependency using constructor injection.
    private final MarketRepository marketRepository;

    /**
     * Creates a new market based on the provided registration request.
     * @param marketRegistrationRequest The request object containing the market name for registration.
     * @throws IllegalArgumentException If the provided market name is empty or null, or if a market with the same name already exists.
     */
    public void createMarket(MarketRegistrationRequest marketRegistrationRequest) {
        // Validate the request to ensure the market name is not empty or null.
        if (marketRegistrationRequest == null || marketRegistrationRequest.marketName() == null ||
                marketRegistrationRequest.marketName().isEmpty()) {
            throw new IllegalArgumentException("Market name cannot be empty or null.");
        }

        // Check if a market with the same name already exists.
        if (marketRepository.findMarketByMarketName(marketRegistrationRequest.marketName()) != null) {
            throw new IllegalArgumentException("A market with the same name already exists.");
        }

        // Create a new Market object with the provided market name.
        Market market = Market.builder().marketName(marketRegistrationRequest.marketName()).build();
        // Save the new market to the database.
        marketRepository.saveAndFlush(market);
    }

    /**
     * Retrieves a market based on the provided market name.
     * @param marketGetRequest The request object containing the market name for retrieval.
     * @return The Market object matching the provided market name.
     * @throws IllegalArgumentException If the provided market name is empty or null.
     * @throws NoSuchElementException If a market with the specified name does not exist in the database.
     */
    public Market getMarketByMarketName(MarketGetRequest marketGetRequest) {
        // Validate the request to ensure the market name is not empty or null.
        if (marketGetRequest == null || marketGetRequest.marketName() == null ||
                marketGetRequest.marketName().isEmpty()) {
            throw new IllegalArgumentException("Market name cannot be empty or null.");
        }

        // Find the market with the specified market name in the database.
        Market market = marketRepository.findMarketByMarketName(marketGetRequest.marketName());

        // Check if the market was found in the database.
        if (market == null) {
            throw new NoSuchElementException("Market not found with the specified name.");
        }

        // Return the retrieved market.
        return market;
    }

    /**
     * Updates an existing market's name based on the provided update request.
     * @param marketUpdateRequest The request object containing the updated market name.
     * @throws IllegalArgumentException If the provided market name is empty or null.
     * @throws NoSuchElementException If a market with the specified name does not exist in the database.
     */
    public void update(MarketUpdateRequest marketUpdateRequest) {
        // Validate the request to ensure the market name is not empty or null.
        if (marketUpdateRequest == null ||
                marketUpdateRequest.oldMarketName() == null ||
                marketUpdateRequest.newMarketName() == null ||
                marketUpdateRequest.oldMarketName().isEmpty() ||
                marketUpdateRequest.newMarketName().isEmpty()
    ) {
            throw new IllegalArgumentException("Market name cannot be empty or null.");
        }

        // Find the market with the specified market name in the database.
        Market market = marketRepository.findMarketByMarketName(marketUpdateRequest.oldMarketName());

        // Check if the market was found in the database.
        if (market == null) {
            throw new NoSuchElementException("Market not found with the specified name.");
        }

        // Update the market name with the new value from the update request.
        market.setMarketName(marketUpdateRequest.newMarketName());
        marketRepository.saveAndFlush(market);
    }

    /**
     * Deletes a market with the specified market name.
     * @param marketDeleteRequest The request object containing the market name to be deleted.
     * @throws IllegalArgumentException If the provided market name is empty or null.
     * @throws NoSuchElementException If a market with the specified name does not exist in the database.
     */
    public void delete(MarketDeleteRequest marketDeleteRequest) {
        // Validate the request to ensure the market name is not empty or null.
        if (marketDeleteRequest == null || marketDeleteRequest.marketName() == null ||
                marketDeleteRequest.marketName().isEmpty()) {
            throw new IllegalArgumentException("Market name cannot be empty or null.");
        }

        // Check if the market exists in the database before deleting it.
        if (marketRepository.findMarketByMarketName(marketDeleteRequest.marketName()) == null) {
            throw new NoSuchElementException("Market not found with the specified name.");
        }

        // Delete the market with the specified market name from the database.
        marketRepository.deleteMarketByMarketName(marketDeleteRequest.marketName());
    }


    public void getAllMarkets() {
        List<Market> markets = marketRepository.findAll();
        markets.forEach(System.out::println);
    }
}
