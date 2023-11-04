package com.example.lab77.Repository;

import com.example.lab77.pojo.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends MongoRepository<Product, String>{
    @Query(value="{productname:'?0'}")
    public Product findByName(String name);

    @Query(value="{_id:'?0'}")
    public Product findByID(String id);
}