package com.example.lab8.query.rest;

import com.example.lab8.query.FindProductsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductQueryController {
    private final QueryGateway queryGateway;

    public ProductQueryController(QueryGateway queryGateway){
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<ProductRestModel> getProducts(){
        FindProductsQuery findProductsQuery = new FindProductsQuery();
        return queryGateway.query(
                findProductsQuery,
                ResponseTypes.multipleInstancesOf(ProductRestModel.class)
                ).join();
    }
}
