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
    MarketService marketService;

    @PostMapping("create")
    public void register(@Valid @RequestBody MarketRegistrationRequest marketRegistrationRequest){
        log.info("register market {}", marketRegistrationRequest.marketName());
        marketService.createMarket(marketRegistrationRequest);
    }
    @PutMapping("/uodate")
    public void update(@Valid @RequestBody MarketUpdateRequest marketUpdateRequest){
        log.info("update market {}", marketUpdateRequest.marketName());
        marketService.update(marketUpdateRequest);
    }
    //delete market
    @DeleteMapping("/delete")
    public void delete(@Valid @RequestBody MarketDeleteRequest marketDeleteRequest){
        log.info("delete market {}", marketDeleteRequest.marketName());
        marketService.delete(marketDeleteRequest);
    }
    //get market by user
    @GetMapping("/get")
    public void getMarketByUser(@Valid @RequestBody MarketGetRequest marketGetRequest){
        log.info("get market {}", marketGetRequest.marketName());
        marketService.getMarketByMarketName(marketGetRequest);
    }
    // all markets
    @GetMapping("/all")
    public void getAllMarkets(){}
}
