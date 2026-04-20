package com.example.projet_moodlets.modele.entites;

import java.io.Serializable;
import java.util.List;

/**
 * Représente une question individuelle au sein d'un quiz.
 * Cette classe est utilisée pour le mapping JSON vers Java.
 */
public class Question implements Serializable {
    private int id, correctOption;
    private String question;
    private List<String> options;

    /**
     * Constructeur complet pour initialiser une question avec ses options.
     */
    public Question(int id, int correctOption, String question, List<String> options) {
        this.id = id;
        this.correctOption = correctOption;
        this.question = question;
        this.options = options;
    }

    /**
     * Constructeur vide nécessaire pour la désérialisation par Jackson/ObjectMapper.
     */
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

    /**
     * @return L'index de la réponse correcte dans la liste des options.
     */
    public int getCorrectOption() {
        return correctOption;
    }

    public String getQuestion() {
        return question;
    }

    /**
     * @return La liste des choix de réponses possibles.
     */
    public List<String> getOptions() {
        return options;
    }
}
