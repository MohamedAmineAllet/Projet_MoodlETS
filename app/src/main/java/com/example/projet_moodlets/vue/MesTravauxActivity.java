package com.example.projet_moodlets.vue;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.modele.daos.Cours.CoursLocalDao;
import com.example.projet_moodlets.modele.daos.Cours.HttpJsonCoursDao;
import com.example.projet_moodlets.modele.daos.Travail.TravauxDaoSingleton;
import com.example.projet_moodlets.modele.entites.Cours;
import com.example.projet_moodlets.modele.entites.Travail;
import com.example.projet_moodlets.vue.adapteurs.TravauxAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Activité gérant l'affichage de la liste des travaux (Assignments).
 * Permet de filtrer par statut (À faire, Remis, etc.) et de rechercher par titre ou cours.
 */
public class MesTravauxActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView lv;
    private TravauxAdapter adapteur;
    private TextView btnTousTravaux, btnAFaire, btnEnRetard, btnRemis, btnCorrige;
    private EditText editTextRecherche;
    private String idCoursRecu;

    private BottomNavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_travaux);

        // Liaison des vues
        lv = findViewById(R.id.lvTravaux);
        btnTousTravaux = findViewById(R.id.txt_filtre_travaux);
        btnAFaire = findViewById(R.id.txt_filtre_A_Faire);
        btnEnRetard = findViewById(R.id.txt_filtre_En_Retard);
        btnRemis = findViewById(R.id.txt_filtre_Remis);
        btnCorrige = findViewById(R.id.txt_filtre_Corrige);
        editTextRecherche = findViewById(R.id.edit_txt_recherche_travail);

        // Récupération de l'ID du cours si l'utilisateur vient de "MesCoursActivity"
        idCoursRecu = getIntent().getStringExtra("ID_COURS");

        // Configuration de l'écouteur unique pour tous les boutons de filtres
        View.OnClickListener ecouteurFiltres = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFiltreClick(v);
            }
        };

        btnTousTravaux.setOnClickListener(ecouteurFiltres);
        btnAFaire.setOnClickListener(ecouteurFiltres);
        btnEnRetard.setOnClickListener(ecouteurFiltres);
        btnRemis.setOnClickListener(ecouteurFiltres);
        btnCorrige.setOnClickListener(ecouteurFiltres);


        lv.setOnItemClickListener(this);

        // Barre de recherche dynamique
        editTextRecherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapteur != null) {
                    adapteur.rechercher(s.toString());
                }
            }
        });

        // Gestion du menu de navigation
        menu = findViewById(R.id.menu_navigation);

        menu.setSelectedItemId(R.id.travaux);


        ViewCompat.setOnApplyWindowInsetsListener(menu, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        menu.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.travaux) {
                return true;
            }

            if (id == R.id.cours) {
                Intent iMesCours = new Intent(this, MesCoursActivity.class);
                iMesCours.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iMesCours);
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.dashboard) {
                Intent iDashboard = new Intent(this, DashBoardActivity.class);
                iDashboard.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iDashboard);
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.quiz) {
                Intent iMesQuiz = new Intent(this, MesQuizActivity.class);
                iMesQuiz.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iMesQuiz);
                overridePendingTransition(0, 0);
                return true;
            }else if (id == R.id.profil){
                Intent iProfil = new Intent(this,MonProfileActivity.class);
                iProfil.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iProfil);
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        menu.setSelectedItemId(R.id.travaux);

        // Vérification des permissions avant le chargement des données
        int r = checkSelfPermission("android.permission.INTERNET");
        if (r == PackageManager.PERMISSION_GRANTED) {
            try {
                obtenirTravaux();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(this, "Non permis!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Envoi vers le détail du travail lors d'un clic
        Intent iTravailDetails = new Intent(this, TravailDetailsActivity.class);
        Travail travailClique = (Travail) adapterView.getAdapter().getItem(i);
        String idTravailClique = String.valueOf(travailClique.getId());
        iTravailDetails.putExtra("ID_TRAVAIL", idTravailClique);
        startActivity(iTravailDetails);
    }

    /**
     * Charge les cours (pour le cache) et les travaux depuis l'API.
     * Gère également le filtrage par cours si un ID a été passé en paramètre.
     */
    private void obtenirTravaux() throws IOException {

        new Thread() {
            @Override
            public void run() {
                // Mise à jour du cache des cours pour l'affichage des titres dans la liste
                HttpJsonCoursDao coursService = new HttpJsonCoursDao();
                List<Cours> cours = coursService.getTousLesCours();

                CoursLocalDao localDao = new CoursLocalDao();
                localDao.remplirCache(cours);

                // Récupération de tous les travaux
                List<Travail> travaux = TravauxDaoSingleton.getInstance().getTravaux();
                List<Travail> travauxAAfficher;

                // Filtrage optionnel : si idCoursRecu n'est pas nul, on ne garde que les travaux de ce cours
                if (idCoursRecu != null && !idCoursRecu.isEmpty()) {
                    int idCours = Integer.parseInt(idCoursRecu);
                    travauxAAfficher = new ArrayList<>();
                    for (Travail t : travaux) {
                        if (t.getCourseId() == idCours) {
                            travauxAAfficher.add(t);
                        }
                    }
                } else {
                    travauxAAfficher = travaux;
                }

                // Mise à jour de l'UI sur le Thread principal
                MesTravauxActivity.this.runOnUiThread(() -> {
                    adapteur = new TravauxAdapter(MesTravauxActivity.this, R.layout.list_travail, travauxAAfficher);
                    lv.setAdapter(adapteur);
                });
            }
        }.start();
    }

    /**
     * Gère le changement visuel des boutons de filtres et notifie l'adapteur.
     */
    private void onFiltreClick(View view) {
        TextView[] tousFiltres = {btnTousTravaux,
                btnAFaire,
                btnEnRetard,
                btnRemis,
                btnCorrige
        };

        TextView tvActif = (TextView) view;
        String filtre = tvActif.getText().toString();
        for (TextView tv : tousFiltres) {
            tv.setBackgroundResource(R.drawable.filtre_travail_ferme);
            tv.setTextColor(Color.parseColor("#B3212022"));
        }


        view.setBackgroundResource(R.drawable.filtre_travail_active);

        adapteur.filtrer(filtre);
    }
}
