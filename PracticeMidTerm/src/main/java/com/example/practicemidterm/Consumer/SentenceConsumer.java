package com.example.practicemidterm.Consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SentenceConsumer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private Sentence sentences;

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