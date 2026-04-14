package com.example.projet_moodlets.modele.daos.Utilisateur;

import com.example.projet_moodlets.modele.entites.Utilisateur;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpsJsonUtilisateurDao implements UtilisateurDao {
    final String URL_POINT_ENTREE = "http://10.0.2.2:3000";
    @Override
    public List<Utilisateur> getUtilisateurs() {
        OkHttpClient client = new OkHttpClient();
        Request requete = new Request.Builder()
                .url(URL_POINT_ENTREE + "/users")
                .build();
        try {
            Response reponse = client.newCall(requete).execute();
            String jsonData = reponse.body().string();
            android.util.Log.d("DAO_USER", "JSON reçu : " + jsonData);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(
                    com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false);

            Utilisateur[] users = mapper.readValue(jsonData, Utilisateur[].class);
            return Arrays.asList(users);
        } catch (IOException ioe) {
            android.util.Log.e("DAO_USER", "Erreur : " + ioe.getMessage());
            throw new RuntimeException(ioe);
        }
    }
    @Override
    public Utilisateur getUtilisateurParEmailEtPassword(String email, String password) {
        List<Utilisateur> users = getUtilisateurs();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equalsIgnoreCase(email) &&users.get(i).getPassword().equals(password)){
                return users.get(i);
            }
        }
        return null;
    }

    @Override
    public Utilisateur getUtilisateurParId(String id) {
        List<Utilisateur> users = getUtilisateurs();
        for(Utilisateur u :users){
            if (u.getId().equals(id)){
                return u;
            }
        }
        return null;
    }

    @Override
    public void ajouterUtilisateur(Utilisateur utilisateurAAjouter) {
        new Thread(() ->{
            try{
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject obj = new JSONObject();
                obj.put("username",utilisateurAAjouter.getUsername());
                obj.put("email",utilisateurAAjouter.getEmail());
                obj.put("password",utilisateurAAjouter.getPassword());
                obj.put("nom",utilisateurAAjouter.getNom());
                obj.put("prenom",utilisateurAAjouter.getPrenom());
                obj.put("telephone",utilisateurAAjouter.getTelephone());
                obj.put("photoUrl",utilisateurAAjouter.getPhotoUrl());

                obj.put("enrolledCourseIds", new JSONArray());
                obj.put("quizResults",new JSONArray());
                obj.put("completedAssignmentIds",new JSONArray());

                RequestBody body = RequestBody.create(obj.toString(),JSON);
                String url = URL_POINT_ENTREE + "/users";
                Request requete = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                try (Response reponse = client.newCall(requete).execute()){
                    if (!reponse.isSuccessful()){
                        throw new RuntimeException("Erreur modification utilisateur");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }).start();
    }

    @Override
    public void supprimerUtilisateur(String id) {
        new Thread(()->{
            try {
                OkHttpClient client = new OkHttpClient();
                String url = URL_POINT_ENTREE + "/users/" + id;
                Request requete = new Request.Builder()
                        .url(url)
                        .delete()
                        .build();
                try (Response response = client.newCall(requete).execute()) {
                    if (!response.isSuccessful()) {
                        throw new RuntimeException("Erreur suppression utilisateur");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void modifierUtilisateur(Utilisateur utilisateurAModifier) {
        new Thread(() ->{
            try{
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject obj = new JSONObject();
                obj.put("id",utilisateurAModifier.getId());
                obj.put("username",utilisateurAModifier.getUsername());
                obj.put("email",utilisateurAModifier.getEmail());
                obj.put("password",utilisateurAModifier.getPassword());
                obj.put("nom",utilisateurAModifier.getNom());
                obj.put("prenom",utilisateurAModifier.getPrenom());
                obj.put("telephone",utilisateurAModifier.getTelephone());
                obj.put("photoUrl",utilisateurAModifier.getPhotoUrl());
                obj.put("enrolledCourseIds",utilisateurAModifier.getEnrolledCourseIds());
                obj.put("quizResults",utilisateurAModifier.getQuizResults());
                obj.put("completedAssignmentIds",utilisateurAModifier.getCompletedAssignmentIds());

                RequestBody body = RequestBody.create(obj.toString(),JSON);
                String url = URL_POINT_ENTREE + "/users/" + utilisateurAModifier.getId();
                Request requete = new Request.Builder()
                        .url(url)
                        .put(body)
                        .build();
                try (Response reponse = client.newCall(requete).execute()){
                    if (!reponse.isSuccessful()){
                        throw new RuntimeException("Erreur modification utilisateur");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }).start();
    }
}
