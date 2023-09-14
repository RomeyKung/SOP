package com.example.lab7.POJO;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
//implement Serializable เพื่อ mapping ง่ายขึ้น
@Data
@Document("Product")
public class Product implements Serializable {
    @Id
    private String _id;
    private String productName;
    private double productCost;
    private double productProfit;
    private double productPrice;
    public Product(){}
    public Product(String _id, String productName, double productCost, double productProfit, double productPrice){
        this._id = _id;
        this.productName = productName;
        this.productCost = productCost;
        this.productProfit = productProfit;
        this.productPrice = productPrice;
    }

}
