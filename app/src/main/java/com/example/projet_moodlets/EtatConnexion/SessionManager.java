package com.example.projet_moodlets.EtatConnexion;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME    = "session_moodlets";
    private static final String KEY_CONNECTE = "est_connecte";
    private static final String KEY_USER_ID  = "user_id";
    private static final String KEY_EMAIL    = "email";
    private static final String KEY_PRENOM   = "prenom";
    private static final String KEY_NOM      = "nom";
    private static final String KEY_PHOTO    = "photo_url";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs  = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    /**
     * Cette méthode permet d'ajouter un utilisateur à la base de données.
     * @param userId
     * @param email
     * @param prenom
     * @param nom
     * @param photoUrl
     */
    public void connecter(String userId, String email,
                          String prenom, String nom, String photoUrl) {
        editor.putBoolean(KEY_CONNECTE, true);
        editor.putString(KEY_USER_ID,   userId);
        editor.putString(KEY_EMAIL,     email);
        editor.putString(KEY_PRENOM,    prenom);
        editor.putString(KEY_NOM,       nom);
        editor.putString(KEY_PHOTO,     photoUrl);
        editor.apply();
    }

    /**
     * Cette méthode permet de retirer l'utilisateur courrant de la mémoire.
     */
    public void deconnecter() {
        editor.clear();
        editor.apply();
    }

    public boolean estConnecte() {
        return prefs.getBoolean(KEY_CONNECTE, false);
    }

    public String getUserId()  { return prefs.getString(KEY_USER_ID, null); }
    public String getEmail()   { return prefs.getString(KEY_EMAIL,   null); }
    public String getPrenom()  { return prefs.getString(KEY_PRENOM,  null); }
    public String getNom()     { return prefs.getString(KEY_NOM,     null); }
    public String getPhotoUrl(){ return prefs.getString(KEY_PHOTO,   null); }
}