package com.example.projet_moodlets.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.adapteurs.TravauxAdapter;
import com.example.projet_moodlets.daos.TravailLocalDao;
import com.example.projet_moodlets.modeles.Travail;

import java.util.List;

public class MesTravauxActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mes_travaux_activity);

        TravailLocalDao daoTravail = TravailLocalDao.getInstance();

        List<Travail> listeDesTravaux = daoTravail.getTravail();

        TravauxAdapter travauxAdapter;

        travauxAdapter =  new TravauxAdapter(this, R.layout.list_travail, listeDesTravaux);

        lv = findViewById(R.id.lvTravaux);
        lv.setAdapter(travauxAdapter);

        lv.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
