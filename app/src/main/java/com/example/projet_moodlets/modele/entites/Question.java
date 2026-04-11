package com.example.projet_moodlets.modele.entites;

import java.util.List;

public class Question {
    private int id, correctOption;
    private String question;
    private List<String> options;

    public Question(int id, int correctOption, String question, List<String> options) {
        this.id = id;
        this.correctOption = correctOption;
        this.question = question;
        this.options = options;
    }

    public Question() {
        this.question = "";
        this.options = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
