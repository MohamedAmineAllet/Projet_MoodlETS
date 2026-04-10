package com.example.projet_moodlets.entites;

public class Horaire {
    private String jour;
    private String heureDebut;
    private String heureFin;
    private String type;
    private String local;

    // Constructeur sans paramètres (Obligatoire pour Jackson)
    public Horaire() {}

    // Constructeur avec paramètres (Pratique pour créer des objets manuellement)
    public Horaire(String jour, String heureDebut, String heureFin, String type, String local) {
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.type = type;
        this.local = local;
    }

    // --- Getters et Setters ---

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

}
