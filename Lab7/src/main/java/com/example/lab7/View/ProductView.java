package com.example.lab7.View;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.awt.*;


@Route("ProductView")
public class ProductView {
    private ComboBox pList;
    private TextField pName;
    private NumberField pCost;
    private NumberField pProfit;
    private NumberField pPrice;

    private Button add;
    private Button up;
    private Button del;
    private Button cls;


    public ProductView() {
        this.pList = new ComboBox<>();
        this.pName = new TextField();
        this.pCost = new NumberField();
        this.pProfit = new NumberField();
        this.pPrice = new NumberField();
        this.add = new Button();
        this.up = new Button();
        this.del = new Button();
        this.cls = new Button();

    }
}
