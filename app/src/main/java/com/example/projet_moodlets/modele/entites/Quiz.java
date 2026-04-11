package com.example.projet_moodlets.modele.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quiz {

    private int id, courseId;

    private Double grade, totalPoints;

    private String title, status, dueDate, submissionDate;

    @JsonProperty("questions")
    private List<Question> questions;

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

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestion() {
        return questions;
    }

    public void setQuestion(List<Question> question) {
        this.questions = question;
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

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
