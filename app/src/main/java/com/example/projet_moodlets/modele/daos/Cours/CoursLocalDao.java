package com.example.projet_moodlets.modele.daos.Cours;

import android.util.Log;

import com.example.projet_moodlets.modele.entites.Cours;
import com.example.projet_moodlets.modele.entites.Travail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoursLocalDao implements CoursDao{
    private List<Cours> cours = new ArrayList<>();


    @Override
    public List<Cours> getTousLesCours() {
        return cours;
    }


    @Override
    public String getTitreParId(String id) {
        android.util.Log.d("DEBUG_CACHE", "Recherche ID: " + id + " dans " + cours.size() + " cours.");
        for (Cours c : cours) {
            String idCache = String.valueOf(c.getId());
            if (idCache.equalsIgnoreCase(id)) {
                return c.getTitle();
            }
        }
        return "Cours #" + id;
    }

    public void remplirCache(List<Cours> nouveauxCours) {
        this.cours.clear();
        this.cours.addAll(nouveauxCours);
    }
}
