package com.example.lab6inclass.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Wizards {
    private ArrayList<Wizard> model;

    public ArrayList<Wizard> getModel() {
        return model;
    }

    public void setModel(ArrayList<Wizard> model) {
        this.model = model;
    }
}
