package com.example.projet_moodlets.vue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import com.example.projet_moodlets.R;
import com.example.projet_moodlets.modele.entites.Annonce;
import com.example.projet_moodlets.modele.entites.Cours;
import com.example.projet_moodlets.modele.entites.Horaire;
import com.example.projet_moodlets.modele.entites.Quiz;
import com.example.projet_moodlets.modele.entites.Travail;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class CoursDetailsActivity extends AppCompatActivity {
    private TextView txtNomCours, txtDetails, txtDescription, txtProf;
    private BottomNavigationView menuNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cours_details);

        // on fait le pont entre le java et le xml pour les textes
        txtNomCours = findViewById(R.id.textView3);
        txtDetails = findViewById(R.id.textView4);
        txtProf = findViewById(R.id.txtEnseignant);
        txtDescription = findViewById(R.id.txt_description_cours);
        menuNavigation = findViewById(R.id.menu_navigation);

        // on recupere l'objet cours complet quon a envoyer par l'intent
        Cours cours = (Cours) getIntent().getSerializableExtra("OBJET_COURS");

        if (cours != null) {
            // on remplit les infos de base du cours
            txtNomCours.setText(cours.getTitle());
            txtDetails.setText(cours.getCode());
            txtProf.setText(cours.getTeacher());
            txtDescription.setText(cours.getDescription());

            // --- Horraire ---
            // on recupere la liste dhoraire pour l'afficher dans le linearlayout
            List<Horaire> horaires = cours.getHoraire();
            if (horaires != null && !horaires.isEmpty()) {
                LinearLayout container = findViewById(R.id.layoutCartesHoraire);
                container.removeAllViews(); // on nettoye le layout au cas ou

                for (Horaire h : horaires) {
                    // on inflate la petite carte xml pour chaque horaire
                    View card = getLayoutInflater().inflate(R.layout.horaire_composante, container, false);
                    // on met les infos dedans a la main
                    remplirCardHoraire(card, h);
                    // on l'ajoute a la liste horizontale
                    container.addView(card);
                }
            }

            // --- ANNONCES ---
            // meme chose pour les annonces du prof
            List<Annonce> annonces = cours.getAnnonces();
            if (annonces != null && !annonces.isEmpty()) {
                LinearLayout container = findViewById(R.id.layoutAnnonce);
                container.removeAllViews();
                for (Annonce a : annonces) {
                    // on gonfle le layout des annonces
                    View card = getLayoutInflater().inflate(R.layout.annonce_composante, container, false);
                    remplirCardAnnonce(card, a);
                    container.addView(card);
                }
            }

            // --- TRAVAUX ---
            // on regarde si on a recu les travaux sinon on les prend direct dans l'objet
            List<Travail> travaux = (List<Travail>) getIntent().getSerializableExtra("TRAVAUX_COURS");
            if (travaux == null) travaux = cours.getassignments();

            if (travaux != null && !travaux.isEmpty()) {
                LinearLayout container = findViewById(R.id.listeTravauxCours);
                container.removeAllViews();
                for (Travail t : travaux) {
                    // inflation du layout pour chaque devoir
                    View item = getLayoutInflater().inflate(R.layout.list_travail, container, false);
                    remplirCardTravail(item, t);
                    container.addView(item);
                }
            }

            // --- QUIZ ---
            // meme chose pour les quiz
            List<Quiz> listeQuiz = (List<Quiz>) getIntent().getSerializableExtra("QUIZ_COURS");
            if (listeQuiz == null && cours.getQuizzes() != null) {
                listeQuiz = cours.getQuizzes();
            }

            if (listeQuiz != null && !listeQuiz.isEmpty()) {
                LinearLayout containerQuiz = findViewById(R.id.listeQuizCours); // l'id qu'on a mis dans le XML
                containerQuiz.removeAllViews();
                for (Quiz q : listeQuiz) {
                    // on gonfle ton layout de quiz (celui avec le RelativeLayout)
                    View viewQuiz = getLayoutInflater().inflate(R.layout.list_quiz, containerQuiz, false);
                    remplirCardQuiz(viewQuiz, q);
                    containerQuiz.addView(viewQuiz);
                }
            }
        }

        // gestion du menu de navigation en bas
        menuNavigation = findViewById(R.id.menu_navigation);
        ViewCompat.setOnApplyWindowInsetsListener(menuNavigation, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        if (menuNavigation != null) {
            menuNavigation.setSelectedItemId(R.id.cours); // on met l'icone cours "allumer" il va etre  en mauve
            menuNavigation.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                // switch entre les differentes activites
                if (id == R.id.travaux) {
                    startActivity(new Intent(this, MesTravauxActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                } else if (id == R.id.dashboard) {
                    startActivity(new Intent(this, DashBoardActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                } else if (id == R.id.quiz) {
                    startActivity(new Intent(this, MesQuizActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                } else if (id == R.id.profil) {
                    startActivity(new Intent(this, MonProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                }
                return true;
            });
        }

        // le bouton pour revenir en arriere
        ImageButton btnRetour = findViewById(R.id.btnRondFlecheGauche);
        if (btnRetour != null) btnRetour.setOnClickListener(v -> finish());
    }

    // petite methode pour remplir la section de travaux avec calcul de %
    private void remplirCardTravail(View v, Travail t) {
        TextView txtTitle = v.findViewById(R.id.txt_nom_travail);
        TextView txtDate = v.findViewById(R.id.txt_date_echeance_travail);
        TextView txtStatut = v.findViewById(R.id.txt_filtre_travail);
        TextView txtCours = v.findViewById(R.id.txt_cours_travail);
        TextView txtScore = v.findViewById(R.id.txt_Score_Pourcentage);

        txtTitle.setText(t.getTitle());
        txtDate.setText(t.getDueDate());
        txtStatut.setText(t.getStatus());
        txtCours.setText(getIntent().getStringExtra("CODE_COURS"));

        // on calcule le pourcentage de la note si on a les points
        if (t.getGrade() != null && t.getTotalPoints() != null) {
            double pourcentage = (t.getGrade() * 100) / t.getTotalPoints();
            txtScore.setText((int) pourcentage + "%");
        } else {
            txtScore.setText("--"); // si pas de note on met des tirets
        }

    }

    //pour mettre les information dans la section
    private void remplirCardQuiz(View v, Quiz q) {
        TextView txtNom = v.findViewById(R.id.txt_nom_quiz);
        TextView txtStatut = v.findViewById(R.id.txt_filtre_quiz);
        TextView txtDate = v.findViewById(R.id.txt_date_echeance_quiz);
        TextView txtScore = v.findViewById(R.id.txt_Score_Pourcentage_quiz);
        TextView txtCours = v.findViewById(R.id.txt_cours_quiz);

        txtNom.setText(q.getTitle());
        txtStatut.setText(q.getStatus());
        txtDate.setText(q.getDueDate());
        txtCours.setText(getIntent().getStringExtra("CODE_COURS"));

        // Gestion du score
        if (q.getGrade() != null && q.getTotalPoints() != null && q.getTotalPoints() > 0) {
            double score = (q.getGrade() * 100) / q.getTotalPoints();
            txtScore.setText((int) score + "%");
        } else {
            txtScore.setText("--");
        }

    }

    // on met les strings dans les textviews des horaires
    private void remplirCardHoraire(View v, Horaire h) {
        ((TextView) v.findViewById(R.id.typeCoursHoraire)).setText(h.getType());
        ((TextView) v.findViewById(R.id.horaireJournee)).setText(h.getJour());
        ((TextView) v.findViewById(R.id.horaireLocal)).setText(h.getLocal());
        ((TextView) v.findViewById(R.id.horaireHeure)).setText(h.getHeureDebut() + " - " + h.getHeureFin());
    }

    // meme chose pour les annonces
    private void remplirCardAnnonce(View v, Annonce a) {
        ((TextView) v.findViewById(R.id.titre_annonce)).setText(a.getTitre());
        ((TextView) v.findViewById(R.id.date_annonce)).setText(a.getDate());
        ((TextView) v.findViewById(R.id.nom_prof_annonce)).setText(a.getAuteur());
        ((TextView) v.findViewById(R.id.contenu_annonce)).setText(a.getDescription_annonce());
    }
}