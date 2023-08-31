package com.example.practicemidterm.View;

import com.example.practicemidterm.Consumer.Sentence;
import com.example.practicemidterm.Publisher.Word;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Route("MyGui")
public class Gui extends HorizontalLayout {
    public Word words = new Word();
    public Gui(){
        TextField addWord = new TextField("Add Word");
        Button goodWord = new Button("Add Good Word");
        Button badWord = new Button("Add Bad Word");

        ComboBox goodBox = new ComboBox("Good Words");

        ComboBox badBox = new ComboBox("Bad Words");

        goodBox.setItems(words.badWords);
        badBox.setItems(words.goodWords);

        goodWord.addClickListener(event->{
            String str = addWord.getValue();
            ArrayList out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/addGood/"+str)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            goodBox.setItems(out);
        });

        badWord.addClickListener(event->{
            String str = addWord.getValue();
            ArrayList out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/addBad/"+str)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            badBox.setItems(out);
        });



        VerticalLayout v1 = new VerticalLayout();
        v1.add(addWord, goodWord, badWord, goodBox, badBox);



        TextField addSentence = new TextField("Add Sentence");
        Button addSentences = new Button("Add Sentence");


        TextArea tagoodSentences = new TextArea("Good Sentences");

        TextArea tabadSentences = new TextArea("Bad Sentences");
        Button showSentence = new Button("Show Sentence");


        addSentences.addClickListener(event->{
            String str = addSentence.getValue();
            String out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/proof/"+str)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            Notification not = new Notification(out);
            not.open();
        });

        showSentence.addClickListener(event->{
            Sentence out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getSentence")
                    .retrieve()
                    .bodyToMono(Sentence.class)
                    .block();
            System.out.println(out.goodSentences);
            tagoodSentences.setValue(out.goodSentences.toString());
            tabadSentences.setValue(out.badSentences.toString());

        });

        VerticalLayout v2 = new VerticalLayout();
        v2.add(addSentence, addSentences, tagoodSentences, tabadSentences, showSentence);
        this.add(v1, v2);
    }
}
