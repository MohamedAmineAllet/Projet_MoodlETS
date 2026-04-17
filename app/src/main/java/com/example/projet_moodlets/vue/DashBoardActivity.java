package com.example.projet_moodlets.vue;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projet_moodlets.R;
import com.example.projet_moodlets.vue.fragementsDashBoard.DashBoardFragement;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // 1. Affichage par défaut du Fragment Dashboard au lancement
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DashBoardFragement())
                    .commit();
        }


        BottomNavigationView menuNavigation = findViewById(R.id.menu_navigation);

        menuNavigation.setSelectedItemId(R.id.dashboard);

        menuNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.travaux) {
                Intent iMesTravaux = new Intent(this, MesTravauxActivity.class);
                iMesTravaux.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iMesTravaux);
                overridePendingTransition(0, 0);
                return true;
            }
            else if (id == R.id.cours) {
                Intent iMesCours = new Intent(this, MesCoursActivity.class);
                iMesCours.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iMesCours);
                overridePendingTransition(0, 0);
                return true;
            }
            else if (id == R.id.dashboard) {
                return true;
            }
            else if (id == R.id.quiz) {
                Intent iMesQuiz = new Intent(this, MesQuizActivity.class);
                iMesQuiz.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iMesQuiz);
                overridePendingTransition(0, 0);
                return true;
            }
            else if (id == R.id.profil) {
                Intent iMonProfil = new Intent(this, MonProfileActivity.class);
                iMonProfil.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iMonProfil);
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
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
            menuNavigation.setSelectedItemId(R.id.dashboard);
        }
    }

}