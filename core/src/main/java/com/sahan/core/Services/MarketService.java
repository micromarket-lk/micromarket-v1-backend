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
        Market market = Market.builder().marketName(marketRegistrationRequest.getMarketName()).build();
        marketRepository.saveAndFlush(market);
    }

    // get
    public void getMarketByMarketName(MarketGetRequest marketGetRequest) {
        Market market = marketRepository.findMarketByMarketName(marketGetRequest.getMarketName());
    }

    // update

    public void update(MarketUpdateRequest marketUpdateRequest) {
        Market market = marketRepository.findMarketByMarketName(marketUpdateRequest.getMarketName());
        market.setMarketName(marketUpdateRequest.getMarketName());
    }

    // delete

    public void delete(MarketDeleteRequest marketDeleteRequest) {
        marketRepository.deleteMarketByMarketName(marketDeleteRequest.getMarketName());
    }
}