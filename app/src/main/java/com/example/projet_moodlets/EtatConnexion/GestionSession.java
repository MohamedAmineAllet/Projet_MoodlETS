package com.example.projet_moodlets.EtatConnexion;

import com.example.projet_moodlets.modele.entites.Utilisateur;

public final class GestionSession {
    private static Utilisateur utilisateurCourrant = null;

    public static Utilisateur getUtilisateurCourrant() {
        return utilisateurCourrant;
    }

    public static void setUtilisateurCourrant(Utilisateur utilisateurCourrant) {
        GestionSession.utilisateurCourrant = utilisateurCourrant;
    }
    public static boolean getEtatUtilisateur() {
        return utilisateurCourrant != null;
    }
}
