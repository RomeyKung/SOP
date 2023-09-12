package com.example.lab6inclass.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

//ทำ mapping schema
//ถ้าต่อ database เมื่อไรให้ implement Serializable เลย
@Data
@Document("Wizard")
public class Wizard implements Serializable {
    @Id
    private String _id;
    private String sex;
    private String name;
    private String position;
    private int money;
    private String school;
    private String house;


    public Wizard() {}

//    public Wizard(String name, String sex, int money, String position, String school, String house) {
//        this.sex = sex;
//        this.name = name;
//        this.school = school;
//        this.house = house;
//        this.money = money;
//        this.position = position;
//    }

    public Wizard(String _id, String name, String sex, int money, String position, String school, String house) {
        this._id = _id;
        this.sex = sex;
        this.name = name;
        this.school = school;
        this.house = house;
        this.money = money;
        this.position = position;
    }
}
