package com.sahan.core.Services;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.sahan.core.Entities.Market.Market;
import com.sahan.core.Requests.Market.MarketDeleteRequest;
import com.sahan.core.Requests.Market.MarketGetRequest;
import com.sahan.core.Requests.Market.MarketRegistrationRequest;
import com.sahan.core.Requests.Market.MarketUpdateRequest;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

@AllArgsConstructor
@SpringBootTest
public class MarketServiceTest {

    private final MarketService marketService;

    @Test
    public void testCreateMarket() {

        // Test successful market creation
        MarketRegistrationRequest request = new MarketRegistrationRequest("New Market");
        marketService.createMarket(request);

        // Test market creation with existing name
        MarketRegistrationRequest requestWithExistingName = new MarketRegistrationRequest("Existing Market");
        try {
            marketService.createMarket(requestWithExistingName);
            fail("Expected IllegalArgumentException for duplicate market name.");
        } catch (IllegalArgumentException e) {
            assertEquals("A market with the same name already exists.", e.getMessage());
        }

        // Test market creation with null market name
        MarketRegistrationRequest requestWithNullName = new MarketRegistrationRequest(null);
        try {
            marketService.createMarket(requestWithNullName);
            fail("Expected IllegalArgumentException for null market name.");
        } catch (IllegalArgumentException e) {
            assertEquals("Market name cannot be empty or null.", e.getMessage());
        }

        // Test market creation with empty market name
        MarketRegistrationRequest requestWithEmptyName = new MarketRegistrationRequest("");
        try {
            marketService.createMarket(requestWithEmptyName);
            fail("Expected IllegalArgumentException for empty market name.");
        } catch (IllegalArgumentException e) {
            assertEquals("Market name cannot be empty or null.", e.getMessage());
        }
    }

    @Test
    public void testGetMarketByMarketName() {
        // Test successful retrieval
        MarketGetRequest request = new MarketGetRequest("Existing Market");
        Market market = marketService.getMarketByMarketName(request);
        assertNotNull(market);
        assertEquals("Existing Market", market.getMarketName());

        // Test retrieval with null market name
        MarketGetRequest requestWithNullName = new MarketGetRequest(null);
        try {
            marketService.getMarketByMarketName(requestWithNullName);
            fail("Expected IllegalArgumentException for null market name.");
        } catch (IllegalArgumentException e) {
            assertEquals("Market name cannot be empty or null.", e.getMessage());
        }

        // Test retrieval with empty market name
        MarketGetRequest requestWithEmptyName = new MarketGetRequest("");
        try {
            marketService.getMarketByMarketName(requestWithEmptyName);
            fail("Expected IllegalArgumentException for empty market name.");
        } catch (IllegalArgumentException e) {
            assertEquals("Market name cannot be empty or null.", e.getMessage());
        }

        // Test retrieval with non-existing market name
        MarketGetRequest requestWithNonExistingName = new MarketGetRequest("Non-Existing Market");
        try {
            marketService.getMarketByMarketName(requestWithNonExistingName);
            fail("Expected NoSuchElementException for non-existing market name.");
        } catch (NoSuchElementException e) {
            assertEquals("Market not found with the specified name.", e.getMessage());
        }
    }

    @Test
    public void testUpdate() {
        // Test successful market update
        MarketUpdateRequest request = new MarketUpdateRequest("Existing Market", "Updated Market");
        marketService.update(request);
        Market updatedMarket = marketService.getMarketByMarketName(new MarketGetRequest("Updated Market"));
        assertNotNull(updatedMarket);

        // Test market update with null market name
        MarketUpdateRequest requestWithNullName = new MarketUpdateRequest(null, "Updated Market");
        try {
            marketService.update(requestWithNullName);
            fail("Expected IllegalArgumentException for null market name.");
        } catch (IllegalArgumentException e) {
            assertEquals("Market name cannot be empty or null.", e.getMessage());
        }

        // Test market update with empty market name
        MarketUpdateRequest requestWithEmptyName = new MarketUpdateRequest("Existing Market", "");
        try {
            marketService.update(requestWithEmptyName);
            fail("Expected IllegalArgumentException for empty market name.");
        } catch (IllegalArgumentException e) {
            assertEquals("Market name cannot be empty or null.", e.getMessage());
        }

        // Test market update with non-existing market name
        MarketUpdateRequest requestWithNonExistingName = new MarketUpdateRequest("Non-Existing Market", "Updated Market");
        try {
            marketService.update(requestWithNonExistingName);
            fail("Expected NoSuchElementException for non-existing market name.");
        } catch (NoSuchElementException e) {
            assertEquals("Market not found with the specified name.", e.getMessage());
        }
    }

    @Test
    public void testDelete() {
        // Test successful market deletion
        MarketDeleteRequest request = new MarketDeleteRequest("Existing Market");
        marketService.delete(request);
        try {
            marketService.getMarketByMarketName(new MarketGetRequest("Existing Market"));
            fail("Expected NoSuchElementException for deleted market name.");
        } catch (NoSuchElementException e) {
            assertEquals("Market not found with the specified name.", e.getMessage());
        }

        // Test market deletion with null market name
        MarketDeleteRequest requestWithNullName = new MarketDeleteRequest(null);
        try {
            marketService.delete(requestWithNullName);
            fail("Expected IllegalArgumentException for null market name.");
        } catch (IllegalArgumentException e) {
            assertEquals("Market name cannot be empty or null.", e.getMessage());
        }

        // Test market deletion with empty market name
        MarketDeleteRequest requestWithEmptyName = new MarketDeleteRequest("");
        try {
            marketService.delete(requestWithEmptyName);
            fail("Expected IllegalArgumentException for empty market name.");
        } catch (IllegalArgumentException e) {
            assertEquals("Market name cannot be empty or null.", e.getMessage());
        }

        // Test market deletion with non-existing market name
        MarketDeleteRequest requestWithNonExistingName = new MarketDeleteRequest("Non-Existing Market");
        try {
            marketService.delete(requestWithNonExistingName);
            fail("Expected NoSuchElementException for non-existing market name.");
        } catch (NoSuchElementException e) {
            assertEquals("Market not found with the specified name.", e.getMessage());
        }
    }
}
