package com.example.lab3.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class MathService {

    @RequestMapping(value= "/add/{a}/{b}")
    public String add(@PathVariable double a,@PathVariable double b){
        return String.valueOf(a+b);
    }

    @RequestMapping(value= "/minus/{a}/{b}")
    public String minus(@PathVariable double a,@PathVariable double b){
        return String.valueOf(a-b);
    }

    @RequestMapping(value = "/multiply")
    public String multiply(@RequestParam double num1, @RequestParam double num2){
        return String.valueOf(num1*num2);
    }

    @RequestMapping(value ="/divide")
    public String divide(@RequestParam double num1,@RequestParam double num2){
        return String.valueOf(num1/num2);
    }

}
