package com.example.lab5.View;

import com.example.lab5.Consumer.Sentence;
import com.example.lab5.Publisher.Word;
import com.example.lab5.Publisher.WordPublisher;
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


@Route("MyView2")
public class MyView2 extends HorizontalLayout {
    public Word words = new Word();
    public MyView2(){
        TextField addWord = new TextField("Add Word");
        Button goodWord = new Button("Add Good Word");
        Button badWord = new Button("Add Bad Word");

        ComboBox goodWords = new ComboBox("Good Words");

        ComboBox badWords = new ComboBox("Bad Words");

        badWords.setItems(words.badWords);
        goodWords.setItems(words.goodWords);

        goodWord.addClickListener(event->{
            String str = addWord.getValue();
            ArrayList out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/addGood/"+str)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            goodWords.setItems(out);
        });

        badWord.addClickListener(event->{
            String str = addWord.getValue();
            ArrayList out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/addBad/"+str)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            badWords.setItems(out);
        });



        VerticalLayout v1 = new VerticalLayout();
        v1.add(addWord, goodWord, badWord, goodWords, badWords);



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
