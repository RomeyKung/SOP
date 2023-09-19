package com.example.lab6addcache.pojo;

import lombok.Data;
import com.example.lab6addcache.pojo.Wizard;
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
