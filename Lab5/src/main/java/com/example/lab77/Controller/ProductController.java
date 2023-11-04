package com.example.lab77.Controller;

import com.example.lab77.Service.CalculatorPriceService;
import com.example.lab77.pojo.Product;
import com.example.lab77.Service.ProductService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PostMapping(value = "/addProduct")
    public boolean serviceAddProduct(@RequestBody Product product) {
        return (boolean) rabbitTemplate.convertSendAndReceive("ProductExchange", "add", product);
    }

    @PostMapping(value = "/updateProduct")
    public List<Product> serviceUpdateProduct(@RequestBody Product product) {
        return (List<Product>) rabbitTemplate.convertSendAndReceive("ProductExchange", "update", product);
    }

    @PostMapping(value = "/deleteProduct/{id}")
    public boolean serviceDeleteProduct(@PathVariable  String id){
        Product product = this.productService.getProductById(id);
        return (boolean) rabbitTemplate.convertSendAndReceive("ProductExchange", "delete", product);
    }
    @GetMapping(value = "/getproductName/{name}")
    public Product serviceGetProductName(@PathVariable String name){
        return (Product) rabbitTemplate.convertSendAndReceive("ProductExchange", "getname", name);
    }
    @GetMapping(value = "/Product")
    public List<Product> serviceGetAllProduct(){
        return (List<Product>) rabbitTemplate.convertSendAndReceive("ProductExchange", "getall", "");
    }
}
