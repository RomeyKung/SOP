package com.example.lab7.View;

import com.example.lab7.POJO.Product;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.stream.Collectors;


@Route("ProductView")
public class ProductView extends VerticalLayout {
    private ComboBox<String> pList;
    private TextField pName;
    private NumberField pCost;
    private NumberField pProfit;
    private NumberField pPrice;

    private Button add;
    private Button up;
    private Button del;
    private Button cls;
    private HorizontalLayout btnPanel;
    private Notification notification;
    private List<Product> products;
    private Product product;

    public ProductView() {

        this.notification = new Notification("", 500);
        this.pList = new ComboBox<>("Product List");
        this.pName = new TextField("Product Name:");
        this.pCost = new NumberField("Product Cost:");
        this.pProfit = new NumberField("Product Profit");
        this.pPrice = new NumberField("Product Price");

        this.btnPanel = new HorizontalLayout();
        this.add = new Button("Add Product");
        this.up = new Button("Update Product");
        this.del = new Button("Delete Product");
        this.cls = new Button("Clear Product");
        this.btnPanel.add(this.add, this.up, this.del, this.cls);
        this.add(pList, pName, pCost, pProfit, pPrice, btnPanel);

        //set
        this.pPrice.setEnabled(false);
        this.pList.addValueChangeListener(e -> {
            //set product ให้เป็นตัวที่หามาจาก pList
            String productName = this.pList.getValue();
            //ติดใน version > 17 นะไอสัส
            //this.product = WebClient.create().get().uri("http://localhost:8080/getProductByName/{name}",productName).retrieve().bodyToMono(Product.class).block();
            this.product = WebClient.create().get().uri("http://localhost:8080/getProductByName/"+productName).retrieve().bodyToMono(Product.class).block();
            if (this.product != null) {
                this.pName.setValue(this.product.getProductName());
                this.pCost.setValue(this.product.getProductCost());
                this.pProfit.setValue(this.product.getProductProfit());
                this.pPrice.setValue(this.product.getProductPrice());
            }
        });

        this.pCost.addKeyPressListener(Key.ENTER, e -> {
            getPrice();
        });
        this.pProfit.addKeyPressListener(Key.ENTER, e -> {
            getPrice();
        });
        this.add.addClickListener(e -> {
            addProduct();
        });
        this.up.addClickListener(e -> {
            updateProduct();
        });
        this.del.addClickListener(e -> {
            delProduct();
        });
        this.cls.addClickListener(e -> {
            clear();
        });

        loadProduct();
        clear();

    }

    public void getPrice() {
        Double cost = this.pCost.getValue();
        Double profit = this.pProfit.getValue();
        Double out = WebClient.create().get().uri("http://localhost:8080/getPrice/" + cost + "/" + profit).retrieve().bodyToMono(Double.class).block();
        this.pPrice.setValue(out);
    }

    public void addProduct() {
        getPrice();
        Product newProduct = new Product(null, this.pName.getValue(), this.pCost.getValue(), this.pProfit.getValue(), this.pPrice.getValue());
        boolean status = WebClient.create().post().uri("http://localhost:8080/addProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newProduct)
                .retrieve().bodyToMono(boolean.class).block();
        notification.setText(status ? "Added" : "something not found");
        notification.open();
        loadProduct();
        clear();
    }

    public void updateProduct() {
        getPrice();
        Product newProduct = new Product(this.product.get_id(), this.pName.getValue(), this.pCost.getValue(), this.pProfit.getValue(), this.pPrice.getValue());
        List<Product> status = WebClient.create().post().uri("http://localhost:8080/updateProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newProduct)
                .retrieve().bodyToMono(List.class).block();
        notification.setText("Updated");
        notification.open();
        clear();
        loadProduct();
    }

    public void delProduct() {
        getPrice();
        Product newProduct = new Product(this.product.get_id(), this.pName.getValue(), this.pCost.getValue(), this.pProfit.getValue(), this.pPrice.getValue());
        boolean status = WebClient.create().post().uri("http://localhost:8080/deleteProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newProduct)
                .retrieve().bodyToMono(boolean.class).block();
        notification.setText(status ? "Deleted" : "something not found");
        notification.open();
        loadProduct();
        clear();
    }

    public void clear() {
        setText("", 0.0, 0.0, 0.0);

    }

    public void setText(String name, Double pCost, Double pProfit, Double pPrice) {
        this.pName.setValue(name);
        this.pCost.setValue(pCost);
        this.pProfit.setValue(pProfit);
        this.pPrice.setValue(pPrice);
    }

    public void loadProduct() {
        this.products = WebClient.create()
                .get()
                .uri("http://localhost:8080/getProductAll")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Product>>() {
                })
                .block();
        updateComboBox();

    }
    public void updateComboBox() {
        List<String> productNames = this.products.stream()
                .map(Product::getProductName)
                .collect(Collectors.toList());
        this.pList.setItems(productNames);
    }
}
