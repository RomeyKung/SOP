package com.example.lab5.Consumer;

import java.io.Serializable;
import java.util.ArrayList;

public class Sentence implements Serializable {
    public ArrayList<String> badSentences;
    public ArrayList<String> goodSentences;
    public Sentence(){
        this.badSentences = new ArrayList<>();
        this.goodSentences = new ArrayList<>();

    }
}
