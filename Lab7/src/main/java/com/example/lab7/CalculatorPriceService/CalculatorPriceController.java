package com.example.lab7.CalculatorPriceService;

import com.example.lab7.POJO.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CalculatorPriceController {

    @Autowired
    private CalculatorPriceService calPriceService;

    @RequestMapping(value = "/getPrice/{cost}/{profit}")
    public Double getPrice(@PathVariable Double cost, @PathVariable Double profit){
        return calPriceService.getPrice(cost, profit);
    }
}
