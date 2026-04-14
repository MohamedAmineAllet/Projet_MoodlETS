package com.example.projet_moodlets.vue;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.modele.daos.Cours.CoursDaoSingleton;
import com.example.projet_moodlets.modele.daos.Cours.CoursLocalDao;
import com.example.projet_moodlets.modele.daos.Cours.HttpJsonCoursDao;
import com.example.projet_moodlets.modele.entites.Cours;
import com.example.projet_moodlets.vue.adapteurs.TravauxAdapter;
import com.example.projet_moodlets.modele.daos.Travail.TravauxDaoSingleton;
import com.example.projet_moodlets.modele.entites.Travail;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MesTravauxActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView lv;
    private TravauxAdapter adapteur;
    private TextView btnTousTravaux, btnAFaire, btnEnRetard, btnRemis, btnCorrige;

    private EditText editTextRecherche;

    private String idCoursRecu;


    private BottomNavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_travaux);

        lv = findViewById(R.id.lvTravaux);
        btnTousTravaux = findViewById(R.id.txt_filtre_travaux);
        btnAFaire = findViewById(R.id.txt_filtre_A_Faire);
        btnEnRetard = findViewById(R.id.txt_filtre_En_Retard);
        btnRemis = findViewById(R.id.txt_filtre_Remis);
        btnCorrige = findViewById(R.id.txt_filtre_Corrige);
        editTextRecherche = findViewById(R.id.edit_txt_recherche_travail);

        //recuperer l'ID du cours si present
        idCoursRecu = getIntent().getStringExtra("ID_COURS");


        View.OnClickListener ecouteurFiltres = new View.OnClickListener(){
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


        editTextRecherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(adapteur != null){
                    adapteur.rechercher(s.toString());
                }
            }
        });


        menu = findViewById(R.id.menu_navigation);

        ViewCompat.setOnApplyWindowInsetsListener(menu, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        menu.setOnItemSelectedListener(item ->{
            int id = item.getItemId();

            if (id == R.id.travaux) {
                return true;
            }

            if(id == R.id.cours){
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

    @Override
    protected void onResume(){
        super.onResume();
        menu.setSelectedItemId(R.id.travaux);

        int r = checkSelfPermission("android.permission.INTERNET");
            if(r == PackageManager.PERMISSION_GRANTED){
                try{
                    obtenirTravaux();
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }else{
                Toast.makeText(this, "Non permis!", Toast.LENGTH_LONG).show();
            }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent iTravailDetails = new Intent(this, TravailDetailsActivity.class);
        Travail travailClique = (Travail) adapterView.getAdapter().getItem(i);
        String idTravailClique = String.valueOf(travailClique.getId());
        iTravailDetails.putExtra("ID_TRAVAIL", idTravailClique);
        startActivity(iTravailDetails);
    }

    private void obtenirTravaux() throws IOException{

        new Thread(){
            @Override
            public void run(){
                HttpJsonCoursDao coursService = new HttpJsonCoursDao();
                List<Cours> cours = coursService.getTousLesCours();

                CoursLocalDao localDao = new CoursLocalDao();
                localDao.remplirCache(cours);

                List<Travail> travaux = TravauxDaoSingleton.getInstance().getTravaux();
                List<Travail> travauxAAfficher;

                // Filtrer par ID de cours si on en a reçu un
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


//                MesTravauxActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapteur = new TravauxAdapter(MesTravauxActivity.this, R.layout.list_travail, travaux);
//                        lv.setAdapter(adapteur);
//                    }
//                });
                MesTravauxActivity.this.runOnUiThread(() -> {
                    adapteur = new TravauxAdapter(MesTravauxActivity.this, R.layout.list_travail, travauxAAfficher);
                    lv.setAdapter(adapteur);
                });
            }
        }.start();
    }

    private void onFiltreClick(View view){
        TextView[] tousFiltres = {btnTousTravaux,
                                    btnAFaire,
                                    btnEnRetard,
                                    btnRemis,
                                    btnCorrige
        };
        
        TextView tvActif = (TextView) view;
        String filtre = tvActif.getText().toString();
        for (TextView tv: tousFiltres) {
            tv.setBackgroundResource(R.drawable.filtre_travail_ferme);
            tv.setTextColor(Color.parseColor("#B3212022"));
        }


        view.setBackgroundResource(R.drawable.filtre_travail_active);

        adapteur.filtrer(filtre);
    }
}
