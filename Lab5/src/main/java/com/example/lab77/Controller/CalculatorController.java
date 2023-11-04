package com.example.lab77.Controller;

import com.example.lab77.Service.CalculatorPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {
    @Autowired
    private CalculatorPriceService calculatorPriceService;
    @GetMapping(value = "/getPrice/{cost}/{profit}")
    public double serviceGetProducts(@PathVariable double cost, @PathVariable double profit){
        return calculatorPriceService.getPrice(cost, profit);
    }
}
