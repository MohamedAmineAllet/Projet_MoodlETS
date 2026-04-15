package com.example.projet_moodlets.modele.daos.Quiz;

import com.example.projet_moodlets.modele.entites.Quiz;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Implémentation du DAO pour récupérer les quiz depuis une API REST (JSON).
 */
public class HttpJsonQuizDao implements QuizDao {
    // URL de l'API (10.0.2.2 pointe vers le localhost de l'ordinateur depuis l'émulateur)
    final String URL_POINT_ENTREE = "http://10.0.2.2:3000";

    /**
     * Récupère la liste complète des quiz via une requête GET.
     */
    @Override
    public List<Quiz> getQuiz() {
        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder().url(URL_POINT_ENTREE + "/quizzes").build();
        Response response = null;

        try {
            response = client.newCall(requete).execute();
            ResponseBody responseBody = response.body();
            String jsonData = responseBody.string();


            ObjectMapper mapper = new ObjectMapper();

            try {
                // Transformation du JSON en tableau d'objets Java (Jackson)
                Quiz[] tabQuiz = mapper.readValue(jsonData, Quiz[].class);
                return Arrays.asList(tabQuiz);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Récupère un quiz spécifique par son identifiant unique.
     */
    @Override
    public Quiz getQuizParId(int id) {
        Quiz quiz = null;
        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder().url(URL_POINT_ENTREE + "/quizzes/" + id).build();
        try {
            Response response = client.newCall(requete).execute();
            ResponseBody responseBody = response.body();
            String jsonData = responseBody.string();

            // Transformation du JSON en un seul objet Quiz
            ObjectMapper mapper = new ObjectMapper();
            quiz = mapper.readValue(jsonData, Quiz.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return quiz;
    }

    /**
     * Met à jour un quiz sur le serveur distant via une requête HTTP PUT.
     * Cette méthode convertit l'objet Quiz en JSON et l'envoie à l'API.
     *
     * @param quiz L'objet Quiz contenant les nouvelles données (note, statut, etc.) à sauvegarder.
     * @throws IOException   Si une erreur de communication réseau survient.
     * @throws JSONException Si la conversion des données JSON échoue.
     */
    @Override
    public void modifier(Quiz quiz) throws IOException, JSONException {
        // Initialisation du client HTTP et du convertisseur JSON (Jackson)
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        try {
            // 1. Conversion de l'objet Java 'quiz' en chaîne de caractères formatée en JSON
            String jsonPourServeur = mapper.writeValueAsString(quiz);

            // 2. Création du corps de la requête avec le JSON et spécification du type MIME (JSON UTF-8)
            RequestBody body = RequestBody.create(jsonPourServeur, MediaType.parse("application/json; charset=utf-8"));

            // 3. Construction de la requête HTTP PUT vers l'URL spécifique du quiz (ex: /quizzes/5)
            Request requete = new Request.Builder().url(URL_POINT_ENTREE + "/quizzes/" + quiz.getId()).put(body).build();

            // 4. Envoi de la requête au serveur et gestion de la réponse
            try (Response response = client.newCall(requete).execute();) {
                // Vérification si le serveur a répondu avec un code de succès (200-299)
                if (!response.isSuccessful()) {
                    throw new IOException("Erreur serveur : " + response.code());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
