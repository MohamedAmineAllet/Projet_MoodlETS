package com.example.projet_moodlets.daos.quiz;

import com.example.projet_moodlets.entites.Quiz;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kotlin.NotImplementedError;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpJsonQuizDao  implements QuizDao{
    final String URL_POINT_ENTREE = "http://10.0.2.2:3000";

    @Override
    public List<Quiz> getQuiz() {
        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder().url(URL_POINT_ENTREE + "/quizzes").build();
        Response response = null;
        try{
            response = client.newCall(requete).execute();
            ResponseBody responseBody = response.body();
            String jsonData = responseBody.string();

            ObjectMapper mapper = new ObjectMapper();

            try{
                Quiz[] tabQuiz = mapper.readValue(jsonData, Quiz[].class);
                return Arrays.asList(tabQuiz);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getTitresDesQuiz() {
        throw new NotImplementedError("Méthode inutile dans cette application");
    }


    @Override
    public Quiz getQuizParId(int id) {
        Quiz quiz = null;
        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder().url(URL_POINT_ENTREE + "/quizzes/" + id).build();
        try{
            Response response = client.newCall(requete).execute();
            ResponseBody responseBody = response.body();
            String jsonData = responseBody.string();

            ObjectMapper mapper = new ObjectMapper();

            quiz = mapper.readValue(jsonData, Quiz.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return quiz;
    }

    @Override
    public void modifier(Quiz quiz) throws IOException, JSONException {

    }
}
