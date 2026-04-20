package com.example.projet_moodlets.vue;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.projet_moodlets.EtatConnexion.SessionManager;
import com.example.projet_moodlets.R;
import com.example.projet_moodlets.modele.daos.Cours.CoursDaoSingleton;
import com.example.projet_moodlets.modele.daos.Quiz.QuizDaoSingleton;
import com.example.projet_moodlets.modele.daos.Travail.TravauxDaoSingleton;
import com.example.projet_moodlets.modele.daos.Utilisateur.UtilisateurDaoSingleton;
import com.example.projet_moodlets.modele.entites.Cours;
import com.example.projet_moodlets.modele.entites.Quiz;
import com.example.projet_moodlets.modele.entites.Travail;
import com.example.projet_moodlets.modele.entites.Utilisateur;
import com.example.projet_moodlets.vue.adapteurs.CoursAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MesCoursActivity extends AppCompatActivity implements OnItemClickListener {

    // la liste qui garde tout les cours en memoire
    private List<Cours> listeDeCours = new ArrayList<>();
    private CoursAdapter adapteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mes_cours);

        // on prepare la listview avec l'adapteur
        ListView lv = findViewById(R.id.lv);
        adapteur = new CoursAdapter(this, R.layout.list_cours, new ArrayList<>(listeDeCours));
        lv.setAdapter(adapteur);
        lv.setOnItemClickListener(this);

        // on essaie de charger les cours au debut
        try {
            obtenirCours();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // gestion du menu en bas
        BottomNavigationView menuNavigation = findViewById(R.id.menu_navigation);
        menuNavigation.setSelectedItemId(R.id.cours);
        ViewCompat.setOnApplyWindowInsetsListener(menuNavigation, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        // quand on clic sur les icones du menu
        menuNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
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

        // barre de recherche pour filtrer les cours par nom ou code
        EditText rechercheCours = findViewById(R.id.recherche_cours);
        rechercheCours.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase().trim();
                List<Cours> resultats = new ArrayList<>();
                for (Cours c : listeDeCours) {
                    // on check si le titre ou le code contient ce qu'on a ecrit
                    if (c.getTitle().toLowerCase().contains(query) || c.getCode().toLowerCase().contains(query)) {
                        resultats.add(c);
                    }
                }
                // on met a jour la liste afficher
                adapteur.filtreListeCours(resultats);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // methode pour recuperer les cours de l'etudiant connecter
    private void obtenirCours() throws IOException {
        SessionManager session = new SessionManager(this);
        String userId = session.getUserId();
        new Thread(() -> {
            try {
                Utilisateur moi = UtilisateurDaoSingleton.getUtilisateurSingleton().getUtilisateurParId(userId);
                List<Cours> coursRecuperes = CoursDaoSingleton.getInstance().getTousLesCours();

                if (moi != null && coursRecuperes != null) {
                    List<String> mesIdsInscrits = moi.getEnrolledCourseIds();
                    List<Cours> coursFiltres = new ArrayList<>();

                    // garde juste les cours ou l'id est dans la liste de l'etudiant
                    for (Cours c : coursRecuperes) {
                        if (mesIdsInscrits != null && mesIdsInscrits.contains(c.getId())) {
                            coursFiltres.add(c);
                        }
                    }

                    // on retourne sur le thread principal pour modifier la vue
                    runOnUiThread(() -> {
                        listeDeCours.clear();
                        listeDeCours.addAll(coursFiltres);
                        adapteur.clear();
                        adapteur.addAll(listeDeCours);
                        adapteur.notifyDataSetChanged();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // quand on clic sur un item de la liste
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cours coursClique = (Cours) parent.getItemAtPosition(position);
        if (coursClique != null) {
            // on va chercher les travaux avant de changer de page
            obtenirTravauxEtOuvrirDetails(coursClique);
        }
    }

    // va chercher les travaux specifiques au cours cliquer
    private void obtenirTravauxEtOuvrirDetails(Cours coursClique) {
        new Thread(() -> {
            ArrayList<Travail> travauxDuCours = new ArrayList<>();
            ArrayList<Quiz> quizDuCours = new ArrayList<>();

            try {
                // on recupere tout les travaux pour filtrer
                List<Travail> tousLesTravaux = TravauxDaoSingleton.getInstance().getTravaux();
                if (tousLesTravaux != null) {
                    for (Travail t : tousLesTravaux) {
                        if (coursClique.getId() != null && coursClique.getId().equals(String.valueOf(t.getCourseId()))) {
                            travauxDuCours.add(t);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                //on recupere les quuiz
                List<Quiz> tousLesQuiz = QuizDaoSingleton.getInstance().getQuiz();
                if (tousLesQuiz != null) {
                    for (Quiz q : tousLesQuiz) {
                        if (coursClique.getId() != null && coursClique.getId().equals(String.valueOf(q.getCourseId()))) {
                            quizDuCours.add(q);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // on lance l'activite de details avec tout les extras
            runOnUiThread(() -> {
                Intent intent = new Intent(MesCoursActivity.this, CoursDetailsActivity.class);
                intent.putExtra("OBJET_COURS", coursClique);
                intent.putExtra("TRAVAUX_COURS", travauxDuCours);
                intent.putExtra("QUIZ_COURS", quizDuCours);
                intent.putExtra("CODE_COURS", coursClique.getCode());

                // On vérifie que l'activité actuelle est toujours là
                if (!isFinishing()) {
                    startActivity(intent);
                }
            });
        }).start();
    }
}