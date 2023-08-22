package com.example.lab5.Publisher;

import com.example.lab5.Consumer.Sentence;
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
    private Word words;
    public WordPublisher(){
        words = new Word();
    }


    @RequestMapping(value = "/addBad/{word}")
    public ArrayList<String> addBadWord(@PathVariable("word") String s) {
        words.badWords.add(s);
        return words.badWords;
    }

    ;

    @RequestMapping(value = "/delBad/{word}")
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s) {
        words.badWords.remove(s);
        return words.badWords;

    }

    ;

    @RequestMapping(value = "/addGood/{word}")
    public ArrayList<String> addGoodWord(@PathVariable("word") String s) {
        words.goodWords.add(s);
        return words.goodWords;
    }

    ;

    @RequestMapping(value = "/delGood/{word}")
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s) {
        words.goodWords.remove(s);
        return words.goodWords;

    }

    @RequestMapping(value = "/proof/{sentence}")
    public String proofSentence(@PathVariable("sentence") String s) {

        boolean good = false;
        boolean bad = false;
        for (String i : this.words.goodWords) {
            if (s.contains(i)) {
                good = true;
            }
        }
        for (String i : this.words.badWords) {
            if (s.contains(i)) {
                bad = true;
            }

        }
        if (good && bad) {
            rabbitTemplate.convertAndSend("Fanout", "", s);
            System.out.println("good and bad na");
            return "Found good and bad";
        } else if (good) {
            rabbitTemplate.convertAndSend("Direct", "GoodWordQueue", s);
            System.out.println("good na");
            return "Found good";


        } else if (bad) {
            rabbitTemplate.convertAndSend("Direct", "BadWordQueue", s);
            System.out.println("bad na");
            return "Found bad";

        }
        return "found notthing";
    }

    @RequestMapping(value = "/getSentence")
    public Sentence getSentences(){
        Sentence sentence = (Sentence)rabbitTemplate.convertSendAndReceive("Direct", "queue", "");
        return sentence;
    }
}
