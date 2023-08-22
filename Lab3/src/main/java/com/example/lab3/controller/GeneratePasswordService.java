package com.example.lab3.controller;
import java.util.Random;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneratePasswordService {

    @RequestMapping(path = "{name:[a-z]+}.generate", method = RequestMethod.GET)
    public String generate(@PathVariable("name") String name){
        Random rand = new Random();
        int min = 100_000_000;
        int max = 999_999_999;
        int pass = (int) (Math.random() * 100000000);

        return  "Hi, "+name+"\n"+"Your new password is "+pass;
    }


}
