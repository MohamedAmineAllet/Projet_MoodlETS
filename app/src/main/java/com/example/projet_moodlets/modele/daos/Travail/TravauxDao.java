package com.example.projet_moodlets.modele.daos.Travail;

import com.example.projet_moodlets.modele.entites.Travail;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Interface définissant les méthodes d'accès aux données pour les Travaux.
 * Permet d'alterner facilement entre une source de données distante (API) ou locale.
 */
public interface TravauxDao {

    /**
     * Récupère la liste complète des travaux.
     * @return Une liste d'objets Travail.
     */
    List<Travail> getTravaux();

    /**
     * Recherche un travail spécifique à l'aide de son titre.
     * @param title Le titre exact du travail.
     * @return L'objet Travail correspondant ou null.
     */
    Travail getTravailParId(String title);

    /**
     * Enregistre les modifications apportées à un travail.
     * @param travail L'objet Travail contenant les nouvelles informations.
     * @throws IOException Erreur lors de la communication avec le serveur.
     * @throws JSONException Erreur lors du traitement du format JSON.
     */
    void modifier(Travail travail) throws IOException, JSONException;
}
