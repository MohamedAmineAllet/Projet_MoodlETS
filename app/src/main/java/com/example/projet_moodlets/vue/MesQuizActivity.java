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
import com.example.projet_moodlets.modele.daos.Cours.CoursDaoSingleton;
import com.example.projet_moodlets.modele.daos.Cours.HttpJsonCoursDao;
import com.example.projet_moodlets.modele.daos.Quiz.QuizDaoSingleton;
import com.example.projet_moodlets.modele.daos.Quiz.QuizLocalDao;
import com.example.projet_moodlets.modele.entites.Cours;
import com.example.projet_moodlets.modele.entites.Quiz;
import com.example.projet_moodlets.vue.adapteurs.QuizAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;

/**
 * Activité principale pour l'affichage et la gestion des quiz de l'utilisateur.
 * Gère la synchronisation entre l'API et la base de données locale, ainsi que les filtres.
 */
public class MesQuizActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv;
    private QuizAdapter adapteur;
    private TextView btnTousQuiz, btnNonCommence, btnTermine;
    private EditText editTextRecherche;
    private BottomNavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_quiz);

        // Initialisation des vues
        lv = findViewById(R.id.lvQuiz);
        btnTousQuiz = findViewById(R.id.txt_filtre_quiz);
        btnNonCommence = findViewById(R.id.txt_filtre_non_commence);
        btnTermine = findViewById(R.id.txt_filtre_termine);
        editTextRecherche = findViewById(R.id.edit_txt_recherche_quiz);

        // Configuration des écouteurs de clics pour les filtres (Tous, À faire, Terminé)
        View.OnClickListener ecouteurFiltres = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFiltreClick(v);
            }
        };

        btnTousQuiz.setOnClickListener(ecouteurFiltres);
        btnNonCommence.setOnClickListener(ecouteurFiltres);
        btnTermine.setOnClickListener(ecouteurFiltres);

        lv.setOnItemClickListener(this);


        // Implémentation de la recherche dynamique (Recherche par titre/cours)
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

        // Configuration de la navigation basse (BottomNavigationView)
        menu = findViewById(R.id.menu_navigation);

        // Correction du padding pour l'encoche (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(menu, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        menu.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.quiz) {
                return true;
            }

            // Navigation entre les différentes sections de l'application
            if (id == R.id.cours) {
                Intent iMesCours = new Intent(this, MesCoursActivity.class);
                iMesCours.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iMesCours);
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.travaux) {
                Intent iMesTravaux = new Intent(this, MesTravauxActivity.class);
                iMesTravaux.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iMesTravaux);
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.dashboard) {
                Intent iDahsboard = new Intent(this, MainActivity.class);
                iDahsboard.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iDahsboard);
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        menu.setSelectedItemId(R.id.quiz);

        // Vérification des permissions Internet avant de lancer la synchronisation
        int r = checkSelfPermission("android.permission.INTERNET");
        if (r == PackageManager.PERMISSION_GRANTED) {
            try {
                obtenirQuiz();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(this, "Non permis!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        // Redirection vers le détail d'un quiz lors du clic sur un élément de la liste
        Intent iQuizDetails = new Intent(this, QuizDetailsActivity.class);
        Quiz quizClique = (Quiz) adapterView.getAdapter().getItem(i);
        String idQuizClique = String.valueOf(quizClique.getId());
        iQuizDetails.putExtra("ID_QUIZ", idQuizClique);
        startActivity(iQuizDetails);
    }

    /**
     * Méthode asynchrone pour charger les cours et les quiz.
     * Effectue la synchronisation avec la base de données locale (SQLite).
     */
    private void obtenirQuiz() throws IOException {
        new Thread() {
            @Override
            public void run() {
                HttpJsonCoursDao coursService = new HttpJsonCoursDao();
                List<Cours> listeCours = coursService.getTousLesCours();
                CoursDaoSingleton.getInstance().remplirCache(listeCours);

                // Récupération des quiz de l'API
                List<Quiz> listeQuiz = QuizDaoSingleton.getInstance().getQuiz();

                QuizLocalDao localDao = new QuizLocalDao(MesQuizActivity.this);

                for (Quiz q : listeQuiz) {
                    localDao.sauvegarderResultatQuiz(q); // Appelle ta méthode pour chaque quiz
                }

                // 4. Mise à jour de l'UI
                MesQuizActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapteur = new QuizAdapter(MesQuizActivity.this, R.layout.list_quiz, listeQuiz);
                        lv.setAdapter(adapteur);
                    }
                });
            }
        }.start();
    }

    /**
     * Gère l'aspect visuel des boutons de filtres et déclenche le filtrage de l'adapteur.
     */
    private void onFiltreClick(View view) {
        TextView[] tousFiltres = {btnTousQuiz, btnNonCommence, btnTermine};

        TextView tvActif = (TextView) view;
        String filtre = tvActif.getText().toString();

        // Réinitialisation du style de tous les boutons
        for (TextView tv : tousFiltres) {
            tv.setBackgroundResource(R.drawable.filtre_quiz_ferme);
            tv.setTextColor(Color.parseColor("#B3212022"));
        }

        view.setBackgroundResource(R.drawable.filtre_quiz_active);

        adapteur.filtrer(filtre);

    }
}
