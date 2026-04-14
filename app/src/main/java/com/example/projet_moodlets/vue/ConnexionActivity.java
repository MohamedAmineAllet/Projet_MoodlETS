package com.example.projet_moodlets.vue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_moodlets.EtatConnexion.SessionManager;
import com.example.projet_moodlets.R;
import com.example.projet_moodlets.modele.daos.Utilisateur.HttpsJsonUtilisateurDao;
import com.example.projet_moodlets.modele.entites.Utilisateur;

public class ConnexionActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnConnexion;
    private TextView tvErreur, tvInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        etEmail       = findViewById(R.id.etEmail);
        etPassword    = findViewById(R.id.etPassword);
        btnConnexion  = findViewById(R.id.btnConnexion);
        tvErreur      = findViewById(R.id.tvErreur);
        tvInscription = findViewById(R.id.tvInscription);

        btnConnexion.setOnClickListener(v -> tenterConnexion());

        tvInscription.setOnClickListener(v ->
                startActivity(new Intent(this, InscriptionActivity.class)));
    }

    private void tenterConnexion() {
        String email    = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validation des champs
        if (email.isEmpty()) {
            etEmail.setError("Champ obligatoire");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Courriel invalide");
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Champ obligatoire");
            return;
        }

        new Thread(()->{
            android.util.Log.d("THREAD", "Thread démarré");

            try {
                android.util.Log.d("THREAD", "Tentative connexion à : " +
                        "http://10.0.2.2:3000/users");
               HttpsJsonUtilisateurDao dao = new HttpsJsonUtilisateurDao();
               Utilisateur utilisateurCourrant = dao.getUtilisateurParEmailEtPassword(email,password);
                android.util.Log.d("THREAD", "Résultat : " + (utilisateurCourrant != null ? utilisateurCourrant.getEmail() : "null"));
                runOnUiThread(()->{
                   if (utilisateurCourrant !=null){
                       SessionManager session = new SessionManager(ConnexionActivity.this);
                       session.connecter(utilisateurCourrant.getId(),
                               utilisateurCourrant.getEmail(),
                               utilisateurCourrant.getPrenom(),
                               utilisateurCourrant.getNom(),
                               utilisateurCourrant.getPhotoUrl());
                       allerAccueil();
                   }else {
                       runOnUiThread(()->{
                           afficherErreur("Courriel ou mot de passe incorrect");
                       });
                   }
               });
           }catch (Exception e){
               e.printStackTrace();
               android.util.Log.e("CONNEXION", "Erreur : " + e.getMessage());
               runOnUiThread(() -> afficherErreur("Erreur : " + e.getMessage()));
           }
        }).start();
    }

    private void allerAccueil() {
        tvErreur.setVisibility(View.GONE);
        //A mettre le dashboard quand se sera bon.

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void afficherErreur(String message) {
        tvErreur.setText(message);
        tvErreur.setVisibility(View.VISIBLE);
    }
}
