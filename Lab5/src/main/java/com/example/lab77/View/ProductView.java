package com.example.lab77.View;

import com.example.lab77.pojo.Product;
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

@Route(value = "/ProductView")
public class ProductView extends VerticalLayout {
    private ComboBox<String> c1;
    private TextField t1;
    private NumberField n1, n2, n3;
    private Button addbtn, upbtn, delbtn, clrbtn;
    private HorizontalLayout h;
    private List<Product> productList;
    private List<String> productNames;
    private Product product;
    public ProductView(){
        h = new HorizontalLayout();
        c1 = new ComboBox<>("Product List");
        t1 = new TextField("Product Name");
        n2 = new NumberField("Product Cost");
        n3 = new NumberField("Product Profit");
        n1 = new NumberField("Product Price");
        n1.setEnabled(false);
        addbtn = new Button("Add Product");
        upbtn = new Button("Update Product");
        delbtn = new Button("Delete Product");
        clrbtn = new Button("Clear Product");
        h.add(addbtn, upbtn, delbtn, clrbtn);
        this.add(c1, t1, n2, n3, n1, h);
        n2.addKeyPressListener(Key.ENTER, event -> {
            getPrice();
        });
        n3.addKeyPressListener(Key.ENTER, event -> {
            getPrice();
        });
        c1.addValueChangeListener(event -> {
            if(c1.getValue() != null){
                product = WebClient.create().get().uri("http://localhost:8080/getproductName/" + c1.getValue())
                        .retrieve().bodyToMono(Product.class)
                        .block();

                if (product != null) {
                    t1.setValue(product.getProductname());
                    n2.setValue(product.getProductcost());
                    n3.setValue(product.getProductprofit());
                    n1.setValue(product.getProductprice());
                }
            }
        });
        addbtn.addClickListener(event -> {
            if(!productNames.contains(t1.getValue())){
                if (!t1.getValue().equals("")){
                    getPrice();
                    Product newProduct = new Product(null, t1.getValue(), n2.getValue(), n3.getValue(), n1.getValue());
                    boolean product = WebClient.create()
                            .post()
                            .uri("http://localhost:8080/addProduct")
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(newProduct)
                            .retrieve()
                            .bodyToMono(boolean.class)
                            .block();
                    if(product){
                        Notification notification = Notification
                                .show("Product Add Success");
                        loadView();
                        clear();
                    }
                }
                else {
                    Notification notification = Notification
                            .show("Product Name is null");
                }

            }
            else{
                Notification notification = Notification
                        .show("Cannot Add because have Product aleardy");
            }
        });

        delbtn.addClickListener(event -> {
            if(c1.getValue() != null){
                boolean res = WebClient.create()
                        .post()
                        .uri("http://localhost:8080/deleteProduct/{id}", product.get_id())
                        .retrieve()
                        .bodyToMono(boolean.class)
                        .block();
                if(res){
                    Notification notification = Notification
                            .show("Delete Product Success");
                    loadView();
                    clear();
                }
            }

        });

        upbtn.addClickListener(event -> {
            if(!t1.getValue().equals("")){
                getPrice();
                Product newProduct = new Product(product.get_id(), t1.getValue(), n2.getValue(), n3.getValue(), n1.getValue());
                productList = WebClient.create()
                        .post()
                        .uri("http://localhost:8080/updateProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(newProduct)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<List<Product>>() {})
                        .block();
                List<String> productNames = productList.stream()
                        .map(Product::getProductname)
                        .collect(Collectors.toList());
                if(productList != null){
                    Notification notification = Notification
                            .show("update Product Success");
                    loadView();
                    clear();
                }
            }else{
                Notification notification = Notification
                        .show("Cannot update because Product Name is null");
            }

        });

        clrbtn.addClickListener(event -> {
            clear();
        });
        loadView();
        clear();
    }
    public void loadView() {
        productList = WebClient.create()
                .get()
                .uri("http://localhost:8080/Product")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Product>>() {})
                .block();

        productNames = productList.stream()
                .map(Product::getProductname)
                .collect(Collectors.toList());
        c1.setItems(productNames);
    }
    public void  clear(){
        t1.setValue("");
        n2.setValue((double) 0);
        n3.setValue((double) 0);
        n1.setValue((double) 0);
    }
    public void getPrice(){
        double res = WebClient.create().get().uri("http://localhost:8080/getPrice/{cost}/{profit}", n2.getValue(), n3.getValue())
                .retrieve().bodyToMono(double.class).block();
        n1.setValue(res);
    }
}
