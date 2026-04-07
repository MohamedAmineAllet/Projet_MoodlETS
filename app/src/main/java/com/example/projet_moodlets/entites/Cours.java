package com.example.projet_moodlets.entites;

import java.util.ArrayList;
import java.util.List;

// entite pour un cours
public class Cours {
    private  int idCours;
    private String codeCours, nomCours, session, nomProfesseur, description;

    private List<Horaire> horaires =new ArrayList<>();

    //constructeur sans parametre
    public Cours(){}

    public Cours(int idCours ,String codeCours, String nomCours, String session, String nomProfesseur, String description){
        this.idCours = idCours;
        this.codeCours = codeCours;
        this.nomCours = nomCours;
        this.session = session;
        this.nomProfesseur = nomProfesseur;
        this.description = description;
    }

    public Cours(int idCours ,String codeCours, String nomCours, String session, String nomProfesseur){
        this.idCours = idCours;
        this.codeCours = codeCours;
        this.nomCours = nomCours;
        this.session = session;
        this.nomProfesseur = nomProfesseur;
    }

    //methode d'acces
    public Integer getId() {
        return idCours;
    }

    public void setId(Integer idCours) {
        this.idCours = idCours;
    }

    public String getCodeCours() {
        return codeCours;
    }

    public void setCodeCours(String codeCours) {
        this.codeCours = codeCours;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

    public String getNomCours() {
        return nomCours;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSession() {
        return session;
    }

    public String getNomProfesseur() {
        return nomProfesseur;
    }

    public void setNomProfesseur(String nomProfesseur) {
        this.nomProfesseur = nomProfesseur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Horaire> getHoraires() {
        return horaires;
    }

    public void setHoraires(List<Horaire> horaires) {
        this.horaires = horaires;
    }

}
