package com.example.practicemidterm.Publisher;

import com.example.practicemidterm.Consumer.Sentence;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class WordPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    protected Word words = new Word();
    @RequestMapping(value = "http://localhost:8080/addBad/{word}")
    public ArrayList<String> addBadWord(@PathVariable String s){
        this.words.badWords.add(s);
        return this.words.badWords;
    }

    @RequestMapping(value = "http://localhost:8080/deleteBad/{word}")
    public ArrayList<String> deleteBadWord(@PathVariable String s){
        this.words.badWords.remove(s);
        return this.words.badWords;
    }
    @RequestMapping(value = "http://localhost:8080/addGood/{word}")
    public ArrayList<String> addGoodWord(@PathVariable String s){
        this.words.goodWords.add(s);
        return this.words.goodWords;
    }
    @RequestMapping(value = "http://localhost:8080/deleteGood/{word}")
    public ArrayList<String> deleteGoodWord(@PathVariable String s){
        this.words.goodWords.remove(s);
        return this.words.goodWords;
    }

    @RequestMapping(value = "http://localhost:8080/proof/{sentence}")
    public void proofSentence(@PathVariable String s){
        boolean good = false;
        boolean bad = false;
        for (String goodWords : this.words.goodWords) {
            good = s.contains(goodWords);
        }
        for (String badWord : this.words.badWords) {
                bad = s.contains(badWord);
        }
        if(good && bad){
            rabbitTemplate.convertAndSend("Fanout", "", s);
            System.out.println("good and bad");
        } else if (good) {
            rabbitTemplate.convertAndSend("Direct","good",s);
            System.out.println("good");
        } else if (bad) {
        rabbitTemplate.convertAndSend("Direct","bad",s);
        System.out.println("bad");
    }
    }
    @RequestMapping(value = "/getSentence")
    public Sentence getSentences(){
        Sentence sentence = (Sentence)rabbitTemplate.convertSendAndReceive("Direct", "queue", "");
        return sentence;
    }
}
