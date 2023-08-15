package com.example.lab4.views;

import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;


@Route(value = "index1")
public class MathView  extends VerticalLayout {
   public MathView(){
       TextField n1 = new TextField("Number 1");
       TextField n2 = new TextField("Number 2");
       TextField ans = new TextField("Answer");
       VerticalLayout v = new VerticalLayout();
       HorizontalLayout operations = new HorizontalLayout();


       Label op = new Label("operation");
//       TextField op = new TextField("Operation");

       Button plus = new Button("+");
       Button minus = new Button("-");
       Button multi = new Button("X");
       Button divide = new Button("/");
       Button mod = new Button("Mod");
       Button max = new Button("Max");

       operations.add(plus,minus,multi,divide,mod,max);

       v.add(op, operations);
       this.add(n1,n2, v ,ans);


       plus.addClickListener(event->{

           double num1 = Double.parseDouble(n1.getValue());
           double num2 = Double.parseDouble(n2.getValue());
           String out = WebClient.create()
           .get()
           .uri("http://localhost:8080/plus/"+num1+"/"+num2)
           .retrieve()
           .bodyToMono(String.class)
           .block();
           ans.setValue(out);
       });
       minus.addClickListener(event->{

           double num1 = Double.parseDouble(n1.getValue());
           double num2 = Double.parseDouble(n2.getValue());
           String out = WebClient.create()
                   .get()
                   .uri("http://localhost:8080/minus/"+num1+"/"+num2)
                   .retrieve()
                   .bodyToMono(String.class)
                   .block();
           ans.setValue(out);
       });
       multi.addClickListener(event->{

           double num1 = Double.parseDouble(n1.getValue());
           double num2 = Double.parseDouble(n2.getValue());
           String out = WebClient.create()
                   .get()
                   .uri("http://localhost:8080/multi/"+num1+"/"+num2)
                   .retrieve()
                   .bodyToMono(String.class)
                   .block();
           ans.setValue(out);
       });
       divide.addClickListener(event->{

           double num1 = Double.parseDouble(n1.getValue());
           double num2 = Double.parseDouble(n2.getValue());
           String out = WebClient.create().get()
                   .uri("http://localhost:8080/divide/"+num1+"/"+num2)
                   .retrieve()
                   .bodyToMono(String.class)
                   .block();
           ans.setValue(out);
       });
       mod.addClickListener(event->{

           double num1 = Double.parseDouble(n1.getValue());
           double num2 = Double.parseDouble(n2.getValue());
           String out = WebClient.create().get()
                   .uri("http://localhost:8080/mod/"+num1+"/"+num2)
                   .retrieve()
                   .bodyToMono(String.class)
                   .block();
           ans.setValue(out);
       });




       max.addClickListener(event->{
           MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
           formData.add("n1", n1.getValue());
           formData.add("n2", n2.getValue());

           String out = WebClient.create()
                   .post()
                   .uri("http://localhost:8080/max")
                   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                   .body(BodyInserters.fromFormData(formData))
                   .retrieve()
                   .bodyToMono(String.class)
                   .block();
           ans.setValue(out);

       });

   }
}
