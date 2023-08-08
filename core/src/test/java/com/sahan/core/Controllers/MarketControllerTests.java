package com.sahan.core.Controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahan.core.Requests.Market.MarketDeleteRequest;
import com.sahan.core.Requests.Market.MarketGetRequest;
import com.sahan.core.Requests.Market.MarketRegistrationRequest;
import com.sahan.core.Requests.Market.MarketUpdateRequest;
import com.sahan.core.Services.MarketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
public class MarketControllerTests {

    private MockMvc mockMvc;

    @Mock
    private MarketService marketService;

    @InjectMocks
    private MarketController marketController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(marketController).build();
    }

    @Test
    public void testCreateMarket_ValidMarketName_ShouldCallService() throws Exception {
        // Arrange
        String validMarketName = "Test Market";
        MarketRegistrationRequest request = new MarketRegistrationRequest(validMarketName);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/market/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());

        // Verify that the marketService.createMarket() method was called with the correct request object.
        verify(marketService, times(1)).createMarket(request);
    }

    @Test
    public void testUpdateMarket_ValidMarketNames_ShouldCallService() throws Exception {
        // Arrange
        String oldMarketName = "Old Market";
        String newMarketName = "New Market";
        MarketUpdateRequest request = new MarketUpdateRequest(oldMarketName, newMarketName);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/market/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());

        // Verify that the marketService.update() method was called with the correct request object.
        verify(marketService, times(1)).update(request);
    }

    @Test
    public void testDeleteMarket_ValidMarketName_ShouldCallService() throws Exception {
        // Arrange
        String validMarketName = "Test Market";
        MarketDeleteRequest request = new MarketDeleteRequest(validMarketName);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/market/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());

        // Verify that the marketService.delete() method was called with the correct request object.
        verify(marketService, times(1)).delete(request);
    }

    @Test
    public void testGetMarketByUser_ValidMarketName_ShouldCallService() throws Exception {
        // Arrange
        String validMarketName = "Test Market";
        MarketGetRequest request = new MarketGetRequest(validMarketName);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/market/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());

        // Verify that the marketService.getMarketByMarketName() method was called with the correct request object.
        verify(marketService, times(1)).getMarketByMarketName(request);
    }

    // Helper method to convert objects to JSON string for request body
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
