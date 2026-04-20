package com.example.projet_moodlets.modele.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Représente un Quiz complet, incluant ses métadonnées et sa liste de questions.
 * Utilise Jackson pour le mapping automatique des données du serveur.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quiz implements Serializable {

    private int id, courseId;
    private Double grade, totalPoints;
    private String title, status, dueDate, submissionDate;

    @JsonProperty("questions")
    private List<Question> questions;

    /**
     * Constructeur complet pour l'initialisation de l'objet.
     */
    public Quiz(int id, int courseId, Double grade, Double totalPoints, String title, String status, String dueDate, String submissionDate, List<Question> questions) {
        this.id = id;
        this.courseId = courseId;
        this.grade = grade;
        this.totalPoints = totalPoints;
        this.title = title;
        this.status = status;
        this.dueDate = dueDate;
        this.submissionDate = submissionDate;
        this.questions = questions;
    }

    /**
     * Constructeur par défaut requis par Jackson pour la désérialisation.
     */
    public Quiz() {
        this.title = "";
        this.status = "";
        this.dueDate = "";
        this.submissionDate = "";
        this.questions = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    /**
     * @return La liste des questions associées à ce quiz.
     */
    public List<Question> getQuestions() {
        return questions;
    }
}
