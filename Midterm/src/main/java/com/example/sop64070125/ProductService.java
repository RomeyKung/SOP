package com.example.sop64070125;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

//(new Product("Macbook",5 , 65000.0),
// new Product("iPhone",20 , 45000),
// new Product("iPad",10 , 32000),
// new Product("AppleWatch",15 , 15000),
// new Product("Dolls",50 , 100))
@RestController
public class ProductService {
    private ArrayList<Product> products;
    public ProductService(){
        products = new ArrayList<Product>();
        products.add(new Product("Macbook",5 , 65000.0));
        products.add(new Product("iPhone",20 , 45000));
        products.add(new Product("iPad",10 , 32000));
        products.add(new Product("AppleWatch",15 , 15000));
        products.add(new Product("Dolls",50 , 100));

    }

    @RequestMapping(value = "/showAllProduct/")
    public ArrayList<Product> showAllProducts(){
        return this.products;
    }

    @RequestMapping(value = "/findProduct/{name}/")
    public Product findProduct(@PathVariable String name){
        for (Product product: this.products ) {
            if(product.getName().equals(name)){
                return product;
            }
        }

        return null;
    }

    @RequestMapping(value = "/addProduct/{name}/{amount}/{price}/")
    public boolean addProduct(@PathVariable String name,@PathVariable int amount,@PathVariable double price){
        products.add(new Product(name, amount, price));
        return true;
    }

    @RequestMapping(value = "/delProduct/{name}/")
    public boolean delProduct(@PathVariable String name){
        for(Product i: this.products){
            if(i.getName().equals(name)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/updateProduct/{id}/{name}/{amount}/{price}/")
    public boolean updateProduct(@PathVariable int id, @PathVariable String name, @PathVariable int amount, @PathVariable double price){
        for(int i=0 ; i < this.products.size(); i++ ){
           if(i == id){
               this.products.get(i).setName(name);
               this.products.get(i).setAmount(amount);
               this.products.get(i).setPrice(price);
               return true;
           }
        }
        return false;
    }

}
