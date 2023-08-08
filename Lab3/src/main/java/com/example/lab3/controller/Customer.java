package com.example.lab3.controller;

public class Customer {

    private String ID;
    private String name;
    private boolean sex;
    private int age;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(String sex) {
        if(sex.toLowerCase().equals("male")){
            this.sex = false;
        }
        this.sex = true;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Customer() {
        this("", null, "female", 0);
    }
    public Customer(String ID, String n, String s, int a) {
        setID(ID);
        setName(n);
        setSex(s);
        setAge(a);
    }

}
