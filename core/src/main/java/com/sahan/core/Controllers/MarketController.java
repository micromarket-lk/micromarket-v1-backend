package com.sahan.core.Controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/market/")
@AllArgsConstructor
public class MarketController {
    @PostMapping("create")
    public void register(){}
    @PutMapping("/uodate")
    public void update(){}
    //delete market
    @DeleteMapping("/delete")
    public void delete(){}
    //get market by user
    @GetMapping("/get")
    public void getMarketByUser(){}
    // all markets
    @GetMapping("/all")
    public void getAllMarkets(){}
}
