package com.example.projet_moodlets.daos.Travail;

import com.example.projet_moodlets.entites.Travail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import kotlin.NotImplementedError;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HttpJsonTravauxDao implements TravauxDao {

    final String URL_POINT_ENTREE = "http://10.0.2.2:3000";

    @Override
    public List<Travail> getTravaux(){
        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder().url(URL_POINT_ENTREE + "/assignments").build();
        Response response = null;
        try{
            response = client.newCall(requete).execute();
            ResponseBody responseBody = response.body();
            String jsonData = responseBody.string();

            ObjectMapper mapper = new ObjectMapper();
            try{
                Travail[] tabTravaux = mapper.readValue(jsonData, Travail[].class);
                return Arrays.asList(tabTravaux);
            }catch(JsonProcessingException e){
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifier(Travail travail) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                JSONObject obj = new JSONObject();
                obj.put("id", travail.getId());
                obj.put("courseid", travail.getCourseId());
                obj.put("title", travail.getTitle());
                obj.put("description", travail.getDescription());
                obj.put("dueDate", travail.getDueDate());
                obj.put("instructions", travail.getInstructions());
                obj.put("status", travail.getStatus());
                obj.put("grade", travail.getGrade());
                obj.put("comment", travail.getComment());
                obj.put("totalPoints", travail.getTotalPoints());
                obj.put("type", travail.getType());
                obj.put("submissionDate", travail.getSubmissionDate());

                RequestBody body = RequestBody.create(obj.toString(), JSON);
                String url = URL_POINT_ENTREE + "/assignments/" + travail.getId();
                Request request = new Request.Builder().url(url).put(body).build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    @Override
    public List<String> getTitresDesTravaux(){
        throw new NotImplementedError("Méthode inutile dans cette application");
    }

    @Override
    public Travail getTravailParId(String id) {
        Travail travail = null;
        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder().url(URL_POINT_ENTREE + "/assignments/" + id).build();
        try{
            Response response = client.newCall(requete).execute();
            ResponseBody responseBody = response.body();
            String jsonData = responseBody.string();

            ObjectMapper mapper = new ObjectMapper();

            travail = mapper.readValue(jsonData, Travail.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return travail;
    }

}
