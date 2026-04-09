package com.example.projet_moodlets.daos.Travail;

import com.example.projet_moodlets.entites.Travail;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface TravauxDao {

    List<String> getTitresDesTravaux();

    List<Travail> getTravaux();

    Travail getTravailParId(String title);

    void modifier(Travail travail) throws IOException, JSONException;
}
