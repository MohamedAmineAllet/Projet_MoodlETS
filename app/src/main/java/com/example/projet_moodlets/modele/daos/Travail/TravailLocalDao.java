package com.example.projet_moodlets.modele.daos.Travail;

import com.example.projet_moodlets.modele.entites.Travail;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation locale du DAO pour les travaux.
 * Actuellement configurée pour stocker les données en mémoire vive (ArrayList).
 */
public class TravailLocalDao implements TravauxDao {

    private List<Travail> travaux = new ArrayList<>();

    /**
     * Retourne la liste des travaux stockés en mémoire.
     *
     * @return Liste d'objets Travail.
     */
    public List<Travail> getTravaux() {
        return travaux;
    }

    /**
     * Recherche un travail par son titre (insensible à la casse).
     *
     * @param title Le titre du travail recherché.
     * @return L'objet Travail trouvé ou null.
     */
    public Travail getTravailParId(String title) {
        for (Travail t : travaux) {
            if (t.getTitle() != null && t.getTitle().equalsIgnoreCase(title)) {
                return t;
            }
        }
        return null;
    }


    /**
     * Met à jour un travail existant dans la liste locale en le cherchant par son ID.
     *
     * @param travail L'objet contenant les nouvelles données.
     */
    @Override
    public void modifier(Travail travail) throws IOException, JSONException {
        for (int i = 0; i < travaux.size(); i++) {
            // On compare par ID et non par titre, car le titre peut changer ou être identique
            if (travaux.get(i).getId() == travail.getId()) {
                travaux.set(i, travail); // Remplace l'ancien objet par le nouveau
                break;
            }
        }
    }

}


