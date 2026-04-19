package com.example.projet_moodlets.vue;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.example.projet_moodlets.EtatConnexion.SessionManager;
import com.example.projet_moodlets.R;
import com.example.projet_moodlets.modele.daos.Utilisateur.HttpsJsonUtilisateurDao;
import com.example.projet_moodlets.modele.daos.Utilisateur.UtilisateurDaoSingleton;
import com.example.projet_moodlets.modele.entites.Utilisateur;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MonProfileActivity extends AppCompatActivity {
    ImageView imageProfile;
    TextView textNomPrenom;
    // 1. Changé en EditText
    EditText etPrenom, etNom, etEmail, etPhone, etUrl, etMdp;
    Button btnEnregistrer, btnDeconnexion;
    Utilisateur utilisateurCourrant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_profile);

        initialiserVues();

        SessionManager session = new SessionManager(this);
        String userId = session.getUserId();

        btnEnregistrer.setOnClickListener(v -> {
            if (utilisateurCourrant != null) {
                utilisateurCourrant.setPrenom(etPrenom.getText().toString());
                utilisateurCourrant.setNom(etNom.getText().toString());
                utilisateurCourrant.setEmail(etEmail.getText().toString());
                utilisateurCourrant.setTelephone(etPhone.getText().toString());
                utilisateurCourrant.setPhotoUrl(etUrl.getText().toString());
                utilisateurCourrant.setPassword(etMdp.getText().toString());

                UtilisateurDaoSingleton.getUtilisateurSingleton().modifierUtilisateur(utilisateurCourrant);
            }
        });

        btnDeconnexion.setOnClickListener(v -> {
            session.deconnecter();
            Intent i = new Intent(this, ConnexionActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });

        // Chargement des données.
        if (userId != null) {
            new Thread(() -> {
                utilisateurCourrant = UtilisateurDaoSingleton.getUtilisateurSingleton().getUtilisateurParId(userId);
                if (utilisateurCourrant != null) {
                    runOnUiThread(() -> remplirFormulaire(utilisateurCourrant));
                }
            }).start();
        }

        configurerNavigation();
    }

    /**
     * Cette méthode permet de relier les EditText etc du XML au code java.
     */
    private void initialiserVues() {
        imageProfile = findViewById(R.id.iv_profile);
        textNomPrenom = findViewById(R.id.profile_nom_prenom);

        // Cast automatique vers EditText
        etPrenom = findViewById(R.id.et_prenom);
        etNom = findViewById(R.id.et_nom);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etUrl = findViewById(R.id.et_url);
        etMdp = findViewById(R.id.et_mdp);

        btnEnregistrer = findViewById(R.id.button);
        btnDeconnexion = findViewById(R.id.btnDeconnexion);
    }

    /**
     * Cette méthode permet de gerer la logique du Menu en bas de page qui redirige vers la
     * page que représente l'icon dans laquelle on clique dessus.
     */
    private void configurerNavigation() {
        BottomNavigationView menuNavigation = findViewById(R.id.menu_navigation);
        menuNavigation.setSelectedItemId(R.id.profil);

        menuNavigation = findViewById(R.id.menu_navigation);
        ViewCompat.setOnApplyWindowInsetsListener(menuNavigation, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        menuNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.profil) return true;

            Intent intent = null;
            if (id == R.id.travaux) intent = new Intent(this, MesTravauxActivity.class);
            else if (id == R.id.cours) intent = new Intent(this, MesCoursActivity.class);
            else if (id == R.id.dashboard) intent = new Intent(this, DashBoardActivity.class);
            else if (id == R.id.quiz) intent = new Intent(this, MesQuizActivity.class);

            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
            return true;
        });
    }

    /**
     * Cette méthode permet de prendre les informations de l'utilisateur courant pour les afficher.
     * @param user L'utilisateur courrant.
     */
    private void remplirFormulaire(Utilisateur user) {
        textNomPrenom.setText(user.getPrenom() + " " + user.getNom());
        etPrenom.setText(user.getPrenom());
        etNom.setText(user.getNom());
        etEmail.setText(user.getEmail());
        etPhone.setText(user.getTelephone());
        etUrl.setText(user.getPhotoUrl());
        etMdp.setText(user.getPassword());

        if (user.getPhotoUrl() != null && !user.getPhotoUrl().isEmpty()) {
            Glide.with(this).load(user.getPhotoUrl()).circleCrop()
                    .placeholder(R.drawable.circle_gray).error(R.drawable.circle_gray).into(imageProfile);
        }
    }
    /**
     * Cette méthode permet de forcer que lorsque dans le menu on clique sur une icon on force
     * l'affichage que l'icon soit cliquée.
     */
    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView menuNavigation = findViewById(R.id.menu_navigation);
        if (menuNavigation != null) {
            menuNavigation.setSelectedItemId(R.id.profil);
        }
    }
}