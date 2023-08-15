package com.example.lab4.views;

import com.example.lab4.Change;
import com.example.lab4.services.Cashier;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import org.springframework.web.reactive.function.client.WebClient;

@Route(value = "index2")
public class CashierView extends VerticalLayout {
    public CashierView(){
        TextField money = new TextField();
        Button button = new Button("คำนวณเงินทอน");
        button.setPrefixComponent(new Span("$"));
        TextField b1000 = new TextField();
        b1000.setPrefixComponent(new Span("$"));
        TextField b500 = new TextField();
        b500.setPrefixComponent(new Span("$"));

        TextField b100 = new TextField();
        b100.setPrefixComponent(new Span("$"));


        TextField b20 = new TextField();
        b20.setPrefixComponent(new Span("$"));

        TextField b10 = new TextField();
        b10.setPrefixComponent(new Span("$"));

        TextField b5 = new TextField();
        b5.setPrefixComponent(new Span("$"));

        TextField b1 = new TextField();
        b1.setPrefixComponent(new Span("$"));

        button.addClickListener(event->{
            Change out = WebClient.create() // สร้างชองทางการสอสาร
                    .get() // กําหนดรูปแบบการสือสาร
                    .uri("http://localhost:8080/getChange/"+money.getValue())
                    .retrieve() // ให้รอรับข้อมูลกลับมา
                    .bodyToMono(Change.class)
                    .block();

            b1000.setValue(out.getB1000()+"");
            b500.setValue(out.getB500()+"");
            b100.setValue(out.getB100()+"");
            b20.setValue(out.getB20()+"");
            b10.setValue(out.getB10()+"");
            b5.setValue(out.getB5()+"");
            b1.setValue(out.getB1000()+"");


        });
        this.add(money, button,b1000
                ,b500
                ,b100

                ,b20
                ,b10
                ,b5
                ,b1);

    }

}
