package com.example.projet_moodlets.modeles;

public class Travail {

    private int id, courseId;
    private double grade, totalPoints;
    private String title, description, dueDate, instruction, status, comment, type;

    public Travail(int id, int courseId, double grade, double totalPoints, String title, String description, String dueDate, String instruction, String status, String comment, String type) {
        this.id = id;
        this.courseId = courseId;
        this.grade = grade;
        this.totalPoints = totalPoints;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.instruction = instruction;
        this.status = status;
        this.comment = comment;
        this.type = type;
    }

    public Travail(int courseId, String title, String description, String dueDate, String instruction, String status, String type) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.instruction = instruction;
        this.status = status;
        this.type = type;
    }

    public Travail() {
        this.title = "";
        this.description = "";
        this.dueDate = "";
        this.instruction = "";
        this.status = "";
        this.comment = "";
        this.type = type;
    }

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

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
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

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
