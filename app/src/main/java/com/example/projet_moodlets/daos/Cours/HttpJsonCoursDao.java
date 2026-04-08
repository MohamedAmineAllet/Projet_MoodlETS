package com.example.projet_moodlets.daos.Cours;

import com.example.projet_moodlets.entites.Cours;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

            //on declare un nouvel object mapper
            ObjectMapper mapper = new ObjectMapper();
            try{
                //Conversion (désérialisation) du JSON
                Cours[] tabCours = mapper.readValue(jsonData, Cours[].class);
                //retourne la liste des cours
                return Arrays.asList(tabCours);

            }catch (JsonProcessingException e){
                throw new RuntimeException(e);
            }
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
