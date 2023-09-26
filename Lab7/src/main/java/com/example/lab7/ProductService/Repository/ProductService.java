package com.example.lab7.ProductService.Repository;

import com.example.lab7.POJO.Product;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

//value = database ใน redis
@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;


    public ProductService(ProductRepository productRepository){
        this.repository = productRepository;
    }

    @CacheEvict(value = "Product", key="'Product'")
    @RabbitListener(queues = "AddProductQueue")
    public boolean addProduct(Product product){
        try{
            this.repository.save(product);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @CachePut(value = "Product", key="'Product'")
    @RabbitListener(queues = "UpdateProductQueue")
    public List<Product> updateProduct(Product product){
        try{
            this.repository.save(product);
            return this.repository.findAll();
        }catch (Exception e){
            return null;
        }
    }

    @CacheEvict(value = "Product", key="'Product'")
    @RabbitListener(queues = "DeleteProductQueue")
    public boolean deleteProduct(Product product){
        try{
            this.repository.delete(product);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Cacheable(value="Product", key="'Product'")
    @RabbitListener(queues = "GetAllProductQueue")
    public List<Product> getAllProduct(){
        try{
            return repository.findAll();
        }catch (Exception e){
            return null;
        }
    }

//    @Cacheable(value="Product", key="'Product'")
    @RabbitListener(queues = "GetNameProductQueue")
    public Product getProductByName(String productName){
        return repository.findByName(productName);
    }
}
