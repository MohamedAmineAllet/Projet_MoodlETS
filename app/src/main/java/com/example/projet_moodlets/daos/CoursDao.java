package com.example.projet_moodlets.daos;

import com.example.projet_moodlets.entites.Cours;

import java.util.List;

public interface CoursDao {
    List<String> getTitreDesCour();

    List<Cours> getCour();

    Cours getCourParTitre(String title);
}
