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


@Route("mainView")
public class mainView extends VerticalLayout {
    private Text header;
    private TextField name;
    private TextField amount;
    private TextField price;
    private Button left, up, del, right;
    private Notification notification;

    private HorizontalLayout panel;
    private int index;

    private ArrayList<Product> products;

    public mainView(){
        this.header = new Text("Product View");
        this.name = new TextField();
        this.amount = new TextField();
        this.price = new TextField();
        this.panel = new HorizontalLayout();
        this.left = new Button("<=");
        this.right = new Button("=>");
        this.up = new Button("Update Product");
        this.del = new Button("Delete Product");
        this.add(header);
        this.add(name);
        this.add(amount);
        this.add(price);
        panel.add(left);
        panel.add(up);
        panel.add(del);
        panel.add(right);
        this.add(panel);
        this.notification = new Notification("",5000);
        this.products = new ArrayList<Product>();
        products.add(new Product("Macbook",5 , 65000.0));
        products.add(new Product("iPhone",20 , 45000));
        products.add(new Product("iPad",10 , 32000));
        products.add(new Product("AppleWatch",15 , 15000));
        products.add(new Product("Dolls",50 , 100));

        //set default to text
        this.index = 0;

        this.name.setValue(products.get(index).getName());
        this.amount.setValue(products.get(index).getAmount()+"");
        this.price.setValue(products.get(index).getName()+"");


        this.right.addClickListener(buttonClickEvent -> {
            if(index+1 <= products.size()){
                index+=1;
                this.name.setValue(products.get(index).getName());
                this.amount.setValue(products.get(index).getAmount()+"");
                this.price.setValue(products.get(index).getName()+"");
            }
        });

        this.left.addClickListener(buttonClickEvent -> {
            if (index - 1 >= 0) {
                index -= 1;
                this.name.setValue(products.get(index).getName());
                this.amount.setValue(products.get(index).getAmount() + "");
                this.price.setValue(products.get(index).getName() + "");
            }
        });

        this.up.addClickListener(buttonClickEvent -> {
            int id = this.index;
            String name = this.name.getValue();
            int Amount = Integer.parseInt(this.amount.getValue());
            double Price = Double.parseDouble(this.price.getValue());
            boolean pro = WebClient.create().get().uri("http://localhost:8080/updateProduct/"+id+"/"+name+"/"+Amount+"/"+Price+"/")
                    .retrieve()
                    .bodyToMono(boolean.class)
                    .block();

                notification.setText("Product update to system successfully");
                notification.open();

        });

        this.del.addClickListener(buttonClickEvent -> {
            String name = this.name.getValue();

            boolean pro = WebClient.create().get().uri("http://localhost:8080/delProduct/"+name+"/")
                    .retrieve()
                    .bodyToMono(boolean.class)
                    .block();
            notification.setText("Product deleted to system successfully");
            notification.open();
        });



    }



}
