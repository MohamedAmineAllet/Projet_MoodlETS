package com.example.projet_moodlets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_moodlets.R;

public class MainActivity extends AppCompatActivity {

    private Button btnTravaux;

    private Button btnCours;
    private Button btnConnexion;

    private ActivityResultLauncher<Intent> travauxLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        travauxLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Code à exécuter au retour de l'activité si nécessaire
                }
        );


        btnTravaux = findViewById(R.id.btnTravaux);
        btnCours = findViewById(R.id.btncours);
        btnConnexion = findViewById(R.id.btnConnexion);



        btnTravaux.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(MainActivity.this, MesTravauxActivity.class);
                travauxLauncher.launch(intention);
                finish();
            }
        });
        btnCours.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(MainActivity.this, MesCoursActivity.class);
                travauxLauncher.launch(intention);
                finish();
            }
        });
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iConnexion = new Intent(MainActivity.this,ConnexionActivity.class);
                travauxLauncher.launch(iConnexion);
                finish();
            }
        });
    }
}