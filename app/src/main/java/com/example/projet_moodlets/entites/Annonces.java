package com.example.projet_moodlets.entites;

public class Annonces {
    private int idAnnonce;
    private String titre, description, date;

    public Annonces(){}

    //methodes d'acces
    public int getIdAnnonce() {
        return idAnnonce;
    }
    public void setIdAnnonce(int idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }



}
