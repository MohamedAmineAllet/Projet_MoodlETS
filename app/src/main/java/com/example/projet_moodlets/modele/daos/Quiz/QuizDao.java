package com.example.projet_moodlets.modele.daos.Quiz;

import com.example.projet_moodlets.modele.entites.Quiz;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Interface définissant les opérations d'accès aux données (DAO) pour les Quiz.
 * Elle permet d'uniformiser les méthodes, qu'elles proviennent d'une API ou d'une base locale.
 */
public interface QuizDao {

    /**
     * Récupère la liste complète des quiz disponibles.
     *
     * @return Une liste d'objets Quiz.
     */
    List<Quiz> getQuiz();

    /**
     * Récupère la liste complète des quiz disponibles.
     *
     * @return Une liste d'objets Quiz.
     */
    Quiz getQuizParId(int id);

    /**
     * Met à jour les informations d'un quiz (ex: note, statut).
     *
     * @param quiz L'objet Quiz contenant les modifications à enregistrer.
     * @throws IOException   En cas d'erreur de communication (pour le DAO distant).
     * @throws JSONException En cas d'erreur lors du traitement des données JSON.
     */
    void modifier(Quiz quiz) throws IOException, JSONException;

}
