package com.example.projet_moodlets.modele.daos.Cours;

import com.example.projet_moodlets.modele.entites.Cours;

import java.util.List;

// Ce que l'on peut faire
public interface CoursDao {


    List<Cours> getTousLesCours();


    String getTitreParId(String id);

    void remplirCache(List<Cours> nouveauxCours);
}
