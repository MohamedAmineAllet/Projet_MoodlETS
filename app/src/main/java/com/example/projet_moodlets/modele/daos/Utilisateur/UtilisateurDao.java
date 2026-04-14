package com.example.projet_moodlets.modele.daos.Utilisateur;

import com.example.projet_moodlets.modele.entites.Utilisateur;

import java.util.List;

public interface UtilisateurDao {
    public List<Utilisateur> getUtilisateurs();
    public Utilisateur getUtilisateurParEmailEtPassword(String email,String password);
    public Utilisateur getUtilisateurParId(String id);

    public void ajouterUtilisateur(Utilisateur utilisateurAAjouter);
    public void supprimerUtilisateur(String id);
    public void modifierUtilisateur(Utilisateur utilisateurAModifier);
}
