package com.sahan.core.Controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/products/")
@AllArgsConstructor
public class ProductController {
    @GetMapping("getproductbyuser")
    public void getProductByUser(){}
    @GetMapping("getproductbymarket")
    public void getProductByMarket(){}
    @GetMapping("getproductbycategory")
    public void getProductByCategory(){}
    @GetMapping("getproductbyprice")
    public void getProductByPrice(){}
    @GetMapping("getproductbyrating")
    public void getProductByRating(){}
    @GetMapping("getproductbyname")
    public void getProductByName(){}
}
