package com.example.projet_moodlets.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.adapteurs.TravauxAdapter;
import com.example.projet_moodlets.daos.TravauxDaoSingleton;
import com.example.projet_moodlets.entites.Travail;

import java.io.IOException;
import java.util.List;

public class MesTravauxActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mes_travaux_activity);

        //TravailLocalDao daoTravail = TravailLocalDao.getInstance();

       // List<Travail> listeDesTravaux = daoTravail.getTravail();

       // TravauxAdapter travauxAdapter;

       // travauxAdapter =  new TravauxAdapter(this, R.layout.list_travail, listeDesTravaux);

        lv = findViewById(R.id.lvTravaux);
       // lv.setAdapter(travauxAdapter);

        lv.setOnItemClickListener(this);


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
                        TravauxAdapter adapteur = new TravauxAdapter(MesTravauxActivity.this, R.layout.list_travail, travaux);
                        lv.setAdapter(adapteur);
                    }
                });
            }
        }.start();
    }
}
