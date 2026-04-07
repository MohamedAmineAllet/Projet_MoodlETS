package com.example.projet_moodlets.entites;

import java.util.Date;

public class Horaire {
    private int idHoraire;
    private int idCours; //pour le lien avec id du cours
    private String plageHoraire, local ,typeDeCours, jour ;

    //Constructeur
    public Horaire(){}

    public Horaire(int idHoraire, int idCours, String plageHoraire, String local, String typeDeCours, String jour) {
        this.idHoraire = idHoraire;
        this.idCours = idCours;
        this.plageHoraire = plageHoraire;
        this.local = local;
        this.typeDeCours = typeDeCours;
        this.jour = jour;
    }

    //Methodes d'acces

    public int getIdHoraire() {
        return idHoraire;
    }
    public void setIdHoraire(int idHoraire) {
        this.idHoraire = idHoraire;
    }

    public int getIdCours() {
        return idCours;
    }
    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public String getPlageHoraire() {
        return plageHoraire;
    }
    public void setPlageHoraire(String plageHoraire) {
        this.plageHoraire = plageHoraire;
    }

    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }

    public String getTypeDeCours() {
        return typeDeCours;
    }
    public void setTypeDeCours(String typeDeCours) {
        this.typeDeCours = typeDeCours;
    }

    public String getJour() {
        return jour;
    }
    public void setJour(String jour) {
        this.jour = jour;
    }

}
