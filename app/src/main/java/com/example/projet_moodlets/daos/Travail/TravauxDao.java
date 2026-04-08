package com.example.projet_moodlets.daos.Travail;

import com.example.projet_moodlets.entites.Travail;

import java.util.List;

public interface TravauxDao {

    List<String> getTitresDesTravaux();

    List<Travail> getTravaux();

    Travail getTravailParId(String title);
}
