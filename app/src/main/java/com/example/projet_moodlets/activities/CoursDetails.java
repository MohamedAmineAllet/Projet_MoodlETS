package com.example.projet_moodlets.activities;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projet_moodlets.R;

public class CoursDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cours_details);

        // Faire le lien avec les vues du XML
        TextView txtNomCours = findViewById(R.id.textView3);
        TextView txtDetails = findViewById(R.id.textView4);

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