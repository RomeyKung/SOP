package com.example.lab77.pojo;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Data
@Document(collection = "Product")
public class Product implements Serializable {
    @Id
    private String _id;
    private String productname;
    private double productcost;
    private double productprofit;
    private double productprice;

    private static final long serialVersionUID = 1L;


    public Product(){

    }

    public Product(String _id, String productname, double productcost, double productprofit, double productprice) {
        this._id = _id;
        this.productname = productname;
        this.productcost = productcost;
        this.productprofit = productprofit;
        this.productprice = productprice;
    }
}