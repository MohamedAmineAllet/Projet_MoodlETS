package com.example.projet_moodlets.modele.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Représente un travail (assignment) au sein d'un cours.
 * Implémente Serializable pour permettre le passage d'objets entre les activités.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Travail implements Serializable {

    private int id, courseId;
    private Double grade, totalPoints;
    private String title;
    private String description, dueDate, instructions, status, comment, type, submissionDate;

    /**
     * Constructeur complet utilisé pour l'initialisation depuis la base de données ou l'API.
     */
    public Travail(int id, int courseId, Double grade, Double totalPoints, String title, String description, String dueDate, String instructions, String status, String comment, String type, String submissionDate) {
        this.id = id;
        this.courseId = courseId;
        this.grade = grade;
        this.totalPoints = totalPoints;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.instructions = instructions;
        this.status = status;
        this.comment = comment;
        this.type = type;
        this.submissionDate = submissionDate;
    }

    /**
     * Constructeur simplifié pour la création d'un nouveau travail.
     */
    public Travail(int courseId, String title, String description, String dueDate, String instructions, String status, String type) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.instructions = instructions;
        this.status = status;
        this.type = type;
    }

    /**
     * Constructeur par défaut requis par Jackson.
     */
    public Travail() {
        this.title = "";
        this.description = "";
        this.dueDate = "";
        this.instructions = "";
        this.status = "";
        this.comment = "";
        this.type = "";
    }

    /**
     * Constructeur minimaliste avec statut par défaut "Non soumis".
     */
    public Travail(int id, String title, String dueDate) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;

        this.status = "Non soumis";

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

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Double getTotalPoints() {
        return totalPoints;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public String getType() {
        return type;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }
}
