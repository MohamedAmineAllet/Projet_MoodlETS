package com.example.projet_moodlets.modele.daos.Utilisateur;

public class UtilisateurDaoSingleton {
    public static UtilisateurDao utilisateurSingleton = null;
    public static UtilisateurDao getUtilisateurSingleton(){
        if (utilisateurSingleton == null){
            utilisateurSingleton = new HttpsJsonUtilisateurDao();
        }
        return utilisateurSingleton;
    }
}
