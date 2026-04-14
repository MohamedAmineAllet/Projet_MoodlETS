package com.example.projet_moodlets.vue;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.modele.entites.Annonce;
import com.example.projet_moodlets.modele.entites.Horaire;
import com.example.projet_moodlets.modele.entites.Travail;


import java.util.List;

public class CoursDetails extends AppCompatActivity {
    private TextView txtNomCours, txtDetails, txtDescription, txtProf ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cours_details);

        // Faire le lien avec les vues du XML
        txtNomCours = findViewById(R.id.textView3);
        txtDetails = findViewById(R.id.textView4);
        txtProf = findViewById(R.id.txtEnseignant);
        txtDescription= findViewById(R.id.txt_description_cours);

        // Récupérer les données de l'Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String titre = extras.getString("TITRE_COURS");
            String code = extras.getString("CODE_COURS");
            String prof = extras.getString("ENSEIGNANT_COURS");
            String description = extras.getString("DESCRIPTION_COURS");
            List<Horaire> horaires = (List<Horaire>) getIntent().getSerializableExtra("LISTE_HORAIRE");
            List<Annonce> annonces = (List<Annonce>) getIntent().getSerializableExtra("ANNONCES_COURS");
            List<Travail> travaux = (List<Travail>) getIntent().getSerializableExtra("TRAVAUX_COURS");


            if (horaires != null && !horaires.isEmpty()) {
                // LinearLayout pour contenir les cartes :
                LinearLayout container = findViewById(R.id.layoutCartesHoraire);

                for (Horaire h : horaires) {
                    // on "gonfle" (inflate) le layout qui contient l'horaire carte
                    View card = getLayoutInflater().inflate(R.layout.horaire_composante, container, false);
                    // on remplit les données
                    remplirCardHoraire(card, h);
                    // on l'ajoute au container
                    container.addView(card);
                }

            }
            if (annonces != null && !annonces.isEmpty()) {
                // LinearLayout pour contenir les cartes :
                LinearLayout container = findViewById(R.id.layoutAnnonce);

                for(Annonce a: annonces){
                    // on "gonfle" (inflate) le layout qui contient l'annonce carte
                    View card = getLayoutInflater().inflate(R.layout.annonce_composante, container, false);
                    // on remplit les données
                    remplirCardAnnonce(card, a);
                    // on l'ajoute au container
                    container.addView(card);
                }

            }

            if(travaux != null && !travaux.isEmpty()) {
                //  recupere le LinearLayout
                LinearLayout container = findViewById(R.id.listeTravauxCours);

                android.util.Log.d("DEBUG_COURS", "Nombre de travaux reçus : " + travaux.size());

                for (Travail t : travaux) {
                    //  on "gonfle" la vue d'un item
                    View itemTravail = getLayoutInflater().inflate(R.layout.list_travail, container, false);

                    //  on remplit les donnes manuellement
                    remplirCardTravail(itemTravail, t);

                    //  on l'ajoute au container
                    container.addView(itemTravail);
                }
            }else{
                android.util.Log.d("DEBUG_COURS", "La liste des travaux est VIDE ou NULL");
            }

            txtNomCours.setText(titre);
            txtDetails.setText(code);
            txtProf.setText(prof);
            txtDescription.setText(description);

        }

        // gerer le bouton retour (flèche gauche)
        ImageButton btnRetour = findViewById(R.id.btnRondFlecheGauche);
        btnRetour.setOnClickListener(v -> finish());
    }

    private void remplirCardTravail(View v, Travail t) {
        TextView txtTitle = v.findViewById(R.id.txt_nom_travail);
        TextView txtDate = v.findViewById(R.id.txt_date_echeance_travail);
        TextView txtStatut = v.findViewById(R.id.txt_filtre_travail);
        TextView txtCours = v.findViewById(R.id.txt_cours_travail);
        TextView txtScore = v.findViewById(R.id.txt_Score_Pourcentage);

        txtTitle.setText(t.getTitle());
        txtDate.setText(t.getDueDate());
        txtStatut.setText(t.getStatus());

        // pour le nom du cours, on peut réutiliser le code déjà présent dans l'activite
        txtCours.setText(getIntent().getStringExtra("CODE_COURS"));

        if (t.getGrade() != null) {
            double pourcentage = (t.getGrade() * 100) / t.getTotalPoints();
            txtScore.setText((int)pourcentage + "%");
        } else {
            txtScore.setText("--");
        }
    }

    //methode pour rempllir les horraire.
    private void remplirCardHoraire(View v, Horaire h) {
        //les ids dans ma composante pour faire les horaires
        TextView txtType = v.findViewById(R.id.typeCoursHoraire);
        TextView txtJour = v.findViewById(R.id.horaireJournee);
        TextView txtLocal = v.findViewById(R.id.horaireLocal);
        TextView txtHeure = v.findViewById(R.id.horaireHeure);

        txtType.setText(h.getType());
        txtJour.setText(h.getJour());
        txtLocal.setText(h.getLocal());
        txtHeure.setText(h.getHeureDebut() + " - " + h.getHeureFin());
    }

    //methode pour remplir la composante pour  les annonces
    private void remplirCardAnnonce(View v, Annonce a){
        //les ids dans ma composante pour faire les horaires
        TextView txtTitre = v.findViewById(R.id.titre_annonce);
        TextView txtJour = v.findViewById(R.id.date_annonce);
        TextView txtEnseigant = v.findViewById(R.id.nom_prof_annonce);
        TextView txtContenu = v.findViewById(R.id.contenu_annonce);

        txtTitre.setText(a.getTitre());
        txtJour.setText(a.getDate());
        txtEnseigant.setText(a.getAuteur());
        txtContenu.setText(a.getDescription_annonce());
    }
}