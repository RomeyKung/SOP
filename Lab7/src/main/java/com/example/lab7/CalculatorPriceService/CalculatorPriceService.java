package com.example.lab7.CalculatorPriceService;

import org.springframework.stereotype.Service;


@Service
public class CalculatorPriceService {

    public Double getPrice(Double cost, Double profit){
        return cost+profit;
    }
}
