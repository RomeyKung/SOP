package com.example.lab77.Service;


import com.example.lab77.Repository.ProductRepository;
import com.example.lab77.pojo.Product;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @CacheEvict(value = "myProduct", key = "'Product'")
    @RabbitListener(queues = "AddProductQueue")
    public boolean addProduct(Product product) {
        try {
            productRepository.save(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @CachePut(value = "myProduct", key = "'Product'")
    @RabbitListener(queues = "UpdateProductQueue")
    public List<Product> updateProduct(Product product) {
        try {
            productRepository.save(product);
            return productRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    @CacheEvict(value = "myProduct", key = "'Product'")
    @RabbitListener(queues = "DeleteProductQueue")
    public boolean deleteProduct(Product product) {
        try {
            productRepository.delete(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Cacheable(value = "myProduct", key = "'Product'")
    @RabbitListener(queues = "GetAllProductQueue")
    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

//    @Cacheable(value = "myProduct", key = "'Product:' + 'name:'+#name")
    @RabbitListener(queues = "GetNameProductQueue")
    public Product getProductByName(String name) {
        try{
            return this.productRepository.findByName(name);
        }catch (Exception e){
            return null;
        }
    }

    public Product getProductById(String id) {
        return this.productRepository.findByID(id);
    }
}
