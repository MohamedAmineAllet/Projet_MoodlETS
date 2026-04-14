package com.example.projet_moodlets.modele.daos.Utilisateur;

import com.example.projet_moodlets.modele.entites.Utilisateur;

import java.util.List;

public class UtilisateurLocalDao implements UtilisateurDao{
    @Override
    public List<Utilisateur> getUtilisateurs() {
        return null;
    }

    @Override
    public Utilisateur getUtilisateurParEmailEtPassword(String email, String password) {
        return null;
    }

    @Override
    public Utilisateur getUtilisateurParId(String id) {
        return null;
    }

    @Override
    public void ajouterUtilisateur(Utilisateur utilisateurAAjouter) {

    }

    @Override
    public void supprimerUtilisateur(String id) {

    }

    @Override
    public void modifierUtilisateur(Utilisateur utilisateurAModifier) {

    }
}
