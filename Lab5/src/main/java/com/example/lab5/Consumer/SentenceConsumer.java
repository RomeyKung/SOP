package com.example.lab5.Consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Queue;

@Service
public class SentenceConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    protected Sentence sentences;

    public SentenceConsumer(){
        sentences = new Sentence();
    }

    @RabbitListener(queues = "BadWordQueue")
    public void addBadSentence(String s){
        sentences.badSentences.add(s);
        System.out.println(sentences.badSentences);

    }
    @RabbitListener(queues = "GoodWordQueue")
    public void addGoodSentence(String s){
        sentences.goodSentences.add(s);
        System.out.println(sentences.goodSentences);
    }

        @RabbitListener(queues = "GetQueue")
    public Sentence getSentence(){
        return this.sentences;
    }

}
