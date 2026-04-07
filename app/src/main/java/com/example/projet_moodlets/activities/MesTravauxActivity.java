package com.example.projet_moodlets.activities;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.adapteurs.TravauxAdapter;
import com.example.projet_moodlets.daos.TravailLocalDao;
import com.example.projet_moodlets.daos.TravauxDaoSingleton;
import com.example.projet_moodlets.modeles.Travail;

import java.io.IOException;
import java.util.List;

public class MesTravauxActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView lv;
    private TravauxAdapter adapteur;
    private TextView btnTousTravaux, btnAFaire, btnEnRetard, btnRemis, btnCorrige;

    private EditText editTextRecherche;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mes_travaux_activity);

        lv = findViewById(R.id.lvTravaux);
        btnTousTravaux = findViewById(R.id.txt_filtre_travail);
        btnAFaire = findViewById(R.id.txt_filtre_A_Faire);
        btnEnRetard = findViewById(R.id.txt_filtre_En_Retard);
        btnRemis = findViewById(R.id.txt_filtre_Remis);
        btnCorrige = findViewById(R.id.txt_filtre_Corrige);
        editTextRecherche = findViewById(R.id.edit_txt_recherche_travail);

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
    }

    @Override
    protected void onResume(){
        super.onResume();

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void obtenirTravaux() throws IOException{

        new Thread(){
            @Override
            public void run(){
                List<Travail> travaux = TravauxDaoSingleton.getInstance().getTravaux();
                MesTravauxActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapteur = new TravauxAdapter(MesTravauxActivity.this, R.layout.list_travail, travaux);
                        lv.setAdapter(adapteur);
                    }
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
        Toast.makeText(this, "Filtre : " + filtre, Toast.LENGTH_SHORT).show();
    }
}
