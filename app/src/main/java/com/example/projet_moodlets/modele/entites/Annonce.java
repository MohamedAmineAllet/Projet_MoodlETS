package com.example.projet_moodlets.modele.entites;

public class Annonce {
    private String titre;
    private String description_annonce;
    private String auteur;
    private String date;


    public Annonce(){}

    //methodes d'acces
    public String getDescription_annonce() {
        return description_annonce;
    }

    public void setDescription_annonce(String description_annonce) {
        this.description_annonce = description_annonce;
    }

    public String getAuteur() {
        return auteur;
    }
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
