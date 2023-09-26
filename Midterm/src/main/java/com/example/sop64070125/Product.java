package com.example.sop64070125;

public class Product {
    private String name;
    private int amount;
    private double price;
    public Product(){
        this("", 0, 0);
    }
    public Product(String name,int amount,double price){
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
