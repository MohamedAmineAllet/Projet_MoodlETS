package com.example.projet_moodlets.vue;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_moodlets.R;

public class CoursDetails extends AppCompatActivity {
    private TextView txtNomCours, txtDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cours_details);

        // Faire le lien avec les vues du XML
        txtNomCours = findViewById(R.id.textView3);
        txtDetails = findViewById(R.id.textView4);

        // Récupérer les données de l'Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String titre = extras.getString("TITRE_COURS");
            String code = extras.getString("CODE_COURS");

            txtNomCours.setText(titre);
            txtDetails.setText(code);
        }

        // Gérer le bouton retour (flèche gauche)
        ImageButton btnRetour = findViewById(R.id.btnRondFlecheGauche);
        btnRetour.setOnClickListener(v -> finish());
    }
}