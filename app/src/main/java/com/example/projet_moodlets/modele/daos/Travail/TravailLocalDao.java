package com.example.projet_moodlets.modele.daos.Travail;

import com.example.projet_moodlets.modele.entites.Travail;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TravailLocalDao implements TravauxDao {
    private List<Travail> travaux = new ArrayList<>();


    @Override
    public List<String> getTitresDesTravaux() {
        List<String> lesTitres = new ArrayList<>();
        for( Travail t:travaux){
            lesTitres.add(t.getTitle());
        }
        return lesTitres;
    }

    public List<Travail> getTravaux(){
        return travaux;
    }

    public Travail getTravailParId(String title){
        for(Travail t:travaux){
            if (t.getTitle() != null && t.getTitle().equalsIgnoreCase(title)) {
                return t;
            }
        }
        return null;
    }


    @Override
    public void modifier(Travail travail) throws IOException, JSONException{
        for (int i = 0; i < travaux.size(); i++) {
            // On compare par ID et non par titre, car le titre peut changer ou être identique
            if (travaux.get(i).getId() == travail.getId()) {
                travaux.set(i, travail); // Remplace l'ancien objet par le nouveau
                break;
            }
        }
    }



}


