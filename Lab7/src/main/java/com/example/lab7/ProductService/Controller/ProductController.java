package com.example.lab7.ProductService.Controller;


import com.example.lab7.POJO.Product;
import org.atmosphere.config.service.Post;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ProductController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/addProduct", method = POST)
    public boolean addProduct(@RequestBody Product product){
        return (boolean) rabbitTemplate.convertSendAndReceive("ProductExchange", "add", product);

    }
    @RequestMapping(value = "/updateProduct", method = POST)
    public List<Product> updateProduct(@RequestBody Product product){
        return (List<Product>) rabbitTemplate.convertSendAndReceive("ProductExchange", "update", product);

    }
    @RequestMapping(value = "/deleteProduct", method = POST)
    public boolean deleteProduct(@RequestBody Product product){
        return (boolean) rabbitTemplate.convertSendAndReceive("ProductExchange", "delete", product);

    }
    @RequestMapping(value = "/getProductByName/{productName}")
    public Product getProductByName(@PathVariable String productName){
        return (Product) rabbitTemplate.convertSendAndReceive("ProductExchange", "getname", productName);
    }
    @RequestMapping(value = "/getProductAll")
    public List<Product> getProductAll(){
        return (List<Product>) rabbitTemplate.convertSendAndReceive("ProductExchange", "getall","");
    }
}
