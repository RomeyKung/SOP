package com.example.lab7.ProductService.Repository;

import com.example.lab7.POJO.Product;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;


//    @RabbitListener(queues = "")
    public ProductService(ProductRepository productRepository){
        this.repository = productRepository;
    }

    @RabbitListener(queues = "AddProductQueue")
    public boolean addProduct(Product product){
        try{
            this.repository.save(product);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @RabbitListener(queues = "UpdateProductQueue")
    public boolean updateProduct(Product product){
        try{
            this.repository.save(product);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @RabbitListener(queues = "DeleteProductQueue")
    public boolean deleteProduct(Product product){
        try{
            this.repository.delete(product);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @RabbitListener(queues = "GetAllProductQueue")
    public List<Product> getAllProduct(){
        try{
            return repository.findAll();
        }catch (Exception e){
            return null;
        }
    }

    @RabbitListener(queues = "GetNameProductQueue")
    public Product getProductByName(String name){
        return repository.findByName(name);
    }
}