package com.example.projet_moodlets.entites;

import java.util.List;

// entite pour un cours

public class Cours {
    //  noms doivent être IDENTIQUES aux clés du JSON
    private String id;
    private String code;
    private String title;
    private String session;
    private String teacher;
    private String description;
    private String imageCours;

    private List<Annonce> annonces;


    //Constructeur
    public Cours() {}

    // Getters et Setters
    public List<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(List<Annonce> annonces) {
        this.annonces = annonces;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id=id; }

    public String getCode() { return code; }


    public String getTitle() { return title; }


    public String getSession() { return session; }

    public String getTeacher() { return teacher; }


    public String getDescription() { return description; }

    public String getImageCours(){ return imageCours; }



}
