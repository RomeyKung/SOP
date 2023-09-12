package com.example.lab7.POJO;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("Product")
public class Product implements Serializable {
    @Id
    private String _id;
    private String productName;
    private int productCost;
    private int productProfit;
    private int productPrice;
    public Product(){}
    public Product(String _id, String productName, int productCost, int productProfit, int productPrice){
        this._id = _id;
        this.productName = productName;
        this.productCost = productCost;
        this.productProfit = productProfit;
        this.productPrice = productPrice;
    }

}
