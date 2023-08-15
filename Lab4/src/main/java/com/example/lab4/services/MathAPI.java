package com.example.lab4.services;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MathAPI {

    @RequestMapping(value = "/plus/{n1}/{n2}")
    public double myPlus(@PathVariable Double n1, @PathVariable Double n2){
        return n1 + n2;
    }
    @RequestMapping(value = "/minus/{n1}/{n2}")
    public double myMinus(@PathVariable Double n1, @PathVariable Double n2){
        return n1 - n2;
    }
    @RequestMapping(value = "/multi/{n1}/{n2}")
    public double myMulti(@PathVariable Double n1, @PathVariable Double n2){
        return n1 * n2;
    }

    @RequestMapping(value = "/divide/{n1}/{n2}")
    public double myDivide(@PathVariable Double n1, @PathVariable Double n2){
        return n1 / n2;
    }

    @RequestMapping(value = "/mod/{n1}/{n2}")
    public double myMod(@PathVariable Double n1, @PathVariable Double n2){
        return n1 % n2;
    }

    @PostMapping(value = "/max")
    public double myMax(@RequestBody MultiValueMap<String, String> n){
        Map<String, String> d = n.toSingleValueMap();
        double maxVal = Math.max(Double.parseDouble(d.get("n1")), Double.parseDouble(d.get("n2")));
        return maxVal;
    }



}
