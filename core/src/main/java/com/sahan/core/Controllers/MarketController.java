package com.sahan.core.Controllers;

import com.sahan.core.Requests.Market.MarketDeleteRequest;
import com.sahan.core.Requests.Market.MarketGetRequest;
import com.sahan.core.Requests.Market.MarketRegistrationRequest;
import com.sahan.core.Requests.Market.MarketUpdateRequest;
import com.sahan.core.Services.MarketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1/market/")
@AllArgsConstructor
public class MarketController {

    private final MarketService marketService;

    /**
     * Endpoint to create a new market.
     *
     * @param marketRegistrationRequest The request object containing the market name for registration.
     * @throws IllegalArgumentException If the provided market name is invalid.
     */
    @PostMapping("/create")
    public void createMarket(@Valid @RequestBody MarketRegistrationRequest marketRegistrationRequest) {
        // Validate the market name.
        String marketName = marketRegistrationRequest.marketName();
        if (!isValidMarketName(marketName)) {
            throw new IllegalArgumentException("Invalid market name. Market name should be alphanumeric and less than 50 characters.");
        }

        // Log the registration request.
        log.info("Register market: {}", marketName);
        // Call the MarketService to create the market.
        marketService.createMarket(marketRegistrationRequest);
    }

    /**
     * Endpoint to update an existing market's name.
     *
     * @param marketUpdateRequest The request object containing the old and new market names for update.
     * @throws IllegalArgumentException If the provided market name is invalid.
     */
    @PutMapping("/update")
    public void updateMarket(@Valid @RequestBody MarketUpdateRequest marketUpdateRequest) {
        // Validate the market name.
        String oldMarketName = marketUpdateRequest.oldMarketName();
        String newMarketName = marketUpdateRequest.newMarketName();
        if (!isValidMarketName(newMarketName)) {
            throw new IllegalArgumentException("Invalid market name. Market name should be alphanumeric and less than 50 characters.");
        }

        // Log the update request.
        log.info("Update market: {}", newMarketName);
        // Call the MarketService to update the market.
        marketService.update(marketUpdateRequest);
    }

    /**
     * Endpoint to delete a market.
     *
     * @param marketDeleteRequest The request object containing the market name to be deleted.
     * @throws IllegalArgumentException If the provided market name is invalid.
     */
    @DeleteMapping("/delete")
    public void deleteMarket(@Valid @RequestBody MarketDeleteRequest marketDeleteRequest) {
        // Validate the market name.
        String marketName = marketDeleteRequest.marketName();
        if (!isValidMarketName(marketName)) {
            throw new IllegalArgumentException("Invalid market name. Market name should be alphanumeric and less than 50 characters.");
        }

        // Log the delete request.
        log.info("Delete market: {}", marketName);
        // Call the MarketService to delete the market.
        marketService.delete(marketDeleteRequest);
    }

    /**
     * Endpoint to get a market by its name.
     *
     * @param marketGetRequest The request object containing the market name for retrieval.
     * @throws IllegalArgumentException If the provided market name is invalid.
     */
    @GetMapping("/get")
    public void getMarketByUser(@Valid @RequestBody MarketGetRequest marketGetRequest) {
        // Validate the market name.
        String marketName = marketGetRequest.marketName();
        if (!isValidMarketName(marketName)) {
            throw new IllegalArgumentException("Invalid market name. Market name should be alphanumeric and less than 50 characters.");
        }

        // Log the get request.
        log.info("Get market: {}", marketName);
        // Call the MarketService to retrieve the market.
        marketService.getMarketByMarketName(marketGetRequest);
    }

    /**
     * Endpoint to get all markets.
     */
    @GetMapping("/all")
    public void getAllMarkets() {
        // Call the MarketService to retrieve all markets.
        // Note: The implementation of this method is missing, it should be added in MarketService.
        marketService.getAllMarkets();
    }

    /**
     * Validates the market name according to specified rules.
     *
     * @param marketName The market name to be validated.
     * @return True if the market name is valid, false otherwise.
     */
    private boolean isValidMarketName(String marketName) {
        // Perform validation based on business rules.
        // For example, check for alphanumeric characters and maximum length.
        return marketName != null && marketName.matches("^[a-zA-Z0-9 ]{1,50}$");
    }
}

