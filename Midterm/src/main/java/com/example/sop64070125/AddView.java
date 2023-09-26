package com.example.sop64070125;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Route("AddView")
public class AddView extends VerticalLayout {
    private Text header;
    private TextField name;
    private TextField amount;
    private TextField price;
    private Button up, del;
    private Notification notification;

    private HorizontalLayout panel;


    private ArrayList<Product> products;
    public AddView(){
        this.header = new Text("Add Product Form");
        this.name = new TextField();
        name.setPlaceholder("Product Name");
        this.amount = new TextField();
        amount.setPlaceholder("Product Amount");
        this.price = new TextField();
        price.setPlaceholder("Product Price");
        this.panel = new HorizontalLayout();

        this.up = new Button("Add Product");
        this.del = new Button("Clear");
        this.add(header);
        this.add(name);
        this.add(amount);
        this.add(price);
        panel.add(up);
        panel.add(del);
        this.add(panel);
        this.notification = new Notification("",5000);
        this.products = new ArrayList<>();


        this.up.addClickListener(buttonClickEvent -> {

            String name = this.name.getValue();
            int amount = Integer.parseInt(this.amount.getValue());
            double price = Double.parseDouble(this.name.getValue());

            boolean pro = WebClient.create().get().uri("http://localhost:8080/addProduct/"+name+"/"+amount+"/"+price+"/")
                    .retrieve()
                    .bodyToMono(boolean.class)
                    .block();
            if(pro){
                notification.setText("Product added to system successfully");
                notification.open();
            }
        });

        this.del.addClickListener(buttonClickEvent -> {
            this.price.setValue("");
            this.amount.setValue("");
            this.name.setValue("");
        });



    }


}
