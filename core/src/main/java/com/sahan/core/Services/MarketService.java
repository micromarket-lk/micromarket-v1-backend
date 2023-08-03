package com.sahan.core.Services;

import com.sahan.core.Entities.Market.Market;
import com.sahan.core.Repostitories.MarketRepository;
import com.sahan.core.Requests.Market.MarketDeleteRequest;
import com.sahan.core.Requests.Market.MarketGetRequest;
import com.sahan.core.Requests.Market.MarketRegistrationRequest;
import com.sahan.core.Requests.Market.MarketUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MarketService {

    private final MarketRepository marketRepository;

    //create
    public void createMarket(MarketRegistrationRequest marketRegistrationRequest) {
        Market market = Market.builder().marketName(marketRegistrationRequest.marketName()).build();
        marketRepository.saveAndFlush(market);
    }

    // get
    public void getMarketByMarketName(MarketGetRequest marketGetRequest) {
        Market market = marketRepository.findMarketByMarketName(marketGetRequest.marketName());
        
    }

    // update

    public void update(MarketUpdateRequest marketUpdateRequest) {
        Market market = marketRepository.findMarketByMarketName(marketUpdateRequest.marketName());
        market.setMarketName(marketUpdateRequest.marketName());
    }

    // delete

    public void delete(MarketDeleteRequest marketDeleteRequest) {
        marketRepository.deleteMarketByMarketName(marketDeleteRequest.marketName());
    }
}