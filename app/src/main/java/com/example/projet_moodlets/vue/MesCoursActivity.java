package com.example.projet_moodlets.vue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.modele.daos.Cours.CoursDaoSingleton;
import com.example.projet_moodlets.modele.daos.Travail.TravauxDaoSingleton;
import com.example.projet_moodlets.modele.entites.Cours;
import com.example.projet_moodlets.modele.entites.Travail;
import com.example.projet_moodlets.vue.adapteurs.CoursAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MesCoursActivity extends AppCompatActivity implements OnItemClickListener {

    private ListView lv;
    private CoursAdapter adapteur;
    private List<Cours> listeDeCours = new ArrayList<>();
    private BottomNavigationView menuNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mes_cours);

        lv = findViewById(R.id.lv);

        adapteur = new CoursAdapter(this, R.layout.list_cours, listeDeCours);
        lv.setAdapter(adapteur);
        lv.setOnItemClickListener(this);

        try {
            obtenirCours();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        menuNavigation = findViewById(R.id.menu_navigation);
        ViewCompat.setOnApplyWindowInsetsListener(menuNavigation, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        menuNavigation.setOnItemSelectedListener(item ->{
            int id = item.getItemId();

            if (id == R.id.travaux) {
                Intent iMesTravaux = new Intent(this, MesTravauxActivity.class);
                iMesTravaux.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iMesTravaux);
                overridePendingTransition(0, 0);
                return true;
            } else if(id == R.id.cours){
                Intent iMesCours = new Intent(this, MesCoursActivity.class);
                iMesCours.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iMesCours);
                overridePendingTransition(0, 0);
                return true;
            }else if(id == R.id.dashboard){
                Intent iDashboard = new Intent(this, MainActivity.class);
                iDashboard.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iDashboard);
                overridePendingTransition(0, 0);
                return true;
            }else if(id == R.id.quiz){
                Intent iMesQuiz = new Intent(this, MesQuizActivity.class);
                iMesQuiz.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iMesQuiz);
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });

    }

    private void obtenirCours() throws IOException {
        new Thread(() -> {
            try {
                List<Cours> coursRecuperes = CoursDaoSingleton.getInstance().getTousLesCours();
                runOnUiThread(() -> {
                    if (coursRecuperes != null) {
                        listeDeCours.clear();
                        listeDeCours.addAll(coursRecuperes);
                        adapteur.notifyDataSetChanged();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cours coursClique = (Cours) parent.getAdapter().getItem(position);
        obtenirTravauxEtOuvrirDetails(coursClique);
    }

    private void obtenirTravauxEtOuvrirDetails(Cours coursClique) {
        new Thread(() -> {
            try {
                // recupere tous les travaux depuis le serveur
                List<Travail> tousLesTravaux = TravauxDaoSingleton.getInstance().getTravaux();
                ArrayList<Travail> travauxDuCours = new ArrayList<>();

                // filtrer : on compare l'ID du cours avec le courseId du travail
                if (tousLesTravaux != null) {
                    for (Travail t : tousLesTravaux) {
                        // il faut que le id cliquer du cours et et id  du  cours referencer du  assignment soit pareil
                        if (coursClique.getId().equals(String.valueOf(t.getCourseId()))) {
                            travauxDuCours.add(t);
                        }
                    }
                }

                // revenir sur le thread UI pour lancer l'activité de détails
                runOnUiThread(() -> {
                    Intent iCoursDetails = new Intent(MesCoursActivity.this, CoursDetails.class);
                    iCoursDetails.putExtra("TITRE_COURS", coursClique.getTitle());
                    iCoursDetails.putExtra("CODE_COURS", coursClique.getCode());
                    iCoursDetails.putExtra("DESCRIPTION_COURS", coursClique.getDescription());
                    iCoursDetails.putExtra("ENSEIGNANT_COURS", coursClique.getTeacher());

                    // on envoie la liste filtre
                    iCoursDetails.putExtra("TRAVAUX_COURS", travauxDuCours);

                    // les autre donne comme horaire et annonce lie au cours
                    if (coursClique.getHoraire() != null) {
                        iCoursDetails.putExtra("LISTE_HORAIRE", new ArrayList<>(coursClique.getHoraire()));
                    }
                    if (coursClique.getAnnonces() != null) {
                        iCoursDetails.putExtra("ANNONCES_COURS", new ArrayList<>(coursClique.getAnnonces()));
                    } startActivity(iCoursDetails);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}