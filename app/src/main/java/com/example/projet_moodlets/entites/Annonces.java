package com.example.projet_moodlets.entites;

public class Annonces {
    private int idAnnonce;
    private String titre;
    private String description;
    private String date;
    private String auteur;

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


}
