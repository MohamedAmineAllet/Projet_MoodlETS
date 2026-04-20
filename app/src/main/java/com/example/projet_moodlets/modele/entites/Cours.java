package com.example.projet_moodlets.modele.entites;

import java.io.Serializable;
import java.util.List;

// entite pour un cours
public class Cours implements Serializable {
    //  noms doivent être IDENTIQUES aux clés du JSON
    private String id;
    private String code;
    private String title;
    private String session;
    private String teacher;
    private String description;
    private String imageCours;

    private List<Annonce> annonces;
    private List<Horaire> horaire;

    private List<Travail> assignments;



    //Constructeur
    public Cours() {}

    // Getters et Setters

    public List<Travail> getassignments() {
        return assignments;
    }
    public List<Annonce> getAnnonces() {
        return annonces;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id=id; }

    public String getCode() { return code; }


    public String getTitle() { return title; }


    public String getSession() { return session; }

    public String getTeacher() { return teacher; }


    public String getDescription() { return description; }

    public String getImageCours(){ return imageCours; }

    public List<Horaire> getHoraire() {
        return horaire;
    }
    public void setHoraires(List<Horaire> horaire) {
        this.horaire = horaire;
    }
}
