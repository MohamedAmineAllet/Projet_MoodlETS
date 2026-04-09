package com.example.projet_moodlets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.adapteurs.CoursAdapter;
import com.example.projet_moodlets.adapteurs.TravauxAdapter;
import com.example.projet_moodlets.daos.Cours.CoursDaoSingleton;
import com.example.projet_moodlets.daos.Travail.TravauxDaoSingleton;
import com.example.projet_moodlets.entites.Cours;
import com.example.projet_moodlets.entites.Travail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MesCoursActivity extends AppCompatActivity implements OnItemClickListener {

    private ListView lv;
    private CoursAdapter adapteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mes_cours);

        lv = findViewById(R.id.lv);
        List<Cours> listeDeCours = new ArrayList<>();

        adapteur = new CoursAdapter(this, R.layout.list_cours, listeDeCours);
        lv.setAdapter(adapteur);
        lv.setOnItemClickListener(this);
        try {
            obtenirCours();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void obtenirCours() throws IOException {

        new Thread(){
            @Override
            public void run(){
                List<Cours> cours = CoursDaoSingleton.getDaoInstance().getTousLesCours();
                MesCoursActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapteur = new CoursAdapter(MesCoursActivity.this, R.layout.list_cours, cours);
                        lv.setAdapter(adapteur);
                    }
                });
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent iCoursDetails = new Intent(this, CoursDetails.class);
        Cours CoursClique = (Cours) parent.getAdapter().getItem(position);
        String idCoursClique = String.valueOf(CoursClique.getId());
        iCoursDetails.putExtra("ID_TRAVAIL", idCoursClique);
        startActivity(iCoursDetails);
    }
}