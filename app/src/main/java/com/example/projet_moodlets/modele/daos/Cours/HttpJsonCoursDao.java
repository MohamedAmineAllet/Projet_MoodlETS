package com.example.projet_moodlets.modele.daos.Cours;

import android.util.Log;

import com.example.projet_moodlets.modele.entites.Cours;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpJsonCoursDao implements CoursDao {
    //Déclaration d'une constante contenant l'URL du
    //serveur de l'API REST (point d’entrée de l’API REST)

    //Pour qu’une application qui s’exécute sur l’émulateur Android accède à un
    //serveur qui s’exécute sur l’ordinateur sur lequel s’exécute l’émulateur, elle
    //doit utiliser l’adresse spéciale "10.0.2.2"
    final String URL_POINT_ENTREE = "http://10.0.2.2:3000";
    private static List<Cours> cours = null;

    @Override
    public List<Cours> getTousLesCours() {
        //Création d’un client OkHttp qui permettra
        //d’envoyer des requêtes HTTP
        OkHttpClient client = new OkHttpClient();

        //Création d’un objet Request pour préparer la requête HTTP
        Request requete = new Request.Builder().url(URL_POINT_ENTREE + "/courses").build();

        //on declare une variable de type Response qui contiendra la réponse de la requête
        Response response = null;

        try {
            //Envoi de la requête HTTP au serveur et
            //réception de la réponse
            response = client.newCall(requete).execute();

            //Récupération du corps (body) de la réponse HTTP
            ResponseBody responseBody = response.body();

            //Conversion du contenu de la réponse (souvent du JSON) en
            //chaîne de caractères
            String jsonData = responseBody.string();

            // c'est pour voir ce que le json retourne
//            Log.d("DEBUG_JSON", jsonData);

            //on declare un nouvel object mapper
            ObjectMapper mapper = new ObjectMapper();

            try{
                //Conversion (désérialisation) du JSON
                Cours[] tabCours = mapper.readValue(jsonData, Cours[].class);
                List<Cours> liste = Arrays.asList(tabCours);

                remplirCache(liste);
                //retourne la liste des cours
                return liste;

            }catch (JsonProcessingException e){
                throw new RuntimeException(e);
            }
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getTitreParId(String id) {
        if (cours != null && id != null) {
            for (Cours c : cours) {
                // On compare l'ID du cours avec l'ID reçu
                if (String.valueOf(c.getId()).equals(id)) {
                    return c.getTitle();
                }
            }
        }
        return "Cours inconnu";
    }

    @Override
    public void remplirCache(List<Cours> nouveauxCours) {
        cours = nouveauxCours;
        Log.d("DEBUG_MOODLETS", "Cache mis à jour. Nombre de cours : " + (cours != null ? cours.size() : 0));
    }


}
