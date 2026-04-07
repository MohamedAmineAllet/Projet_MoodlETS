package com.example.projet_moodlets.daos;

import com.example.projet_moodlets.entites.Travail;

import java.util.List;

public interface TravauxDao {

    List<String> getTitresDesTravaux();

    List<Travail> getTravaux();

    Travail getTravailParTitre(String title);
}
