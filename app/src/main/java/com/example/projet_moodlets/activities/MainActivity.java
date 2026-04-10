package com.example.projet_moodlets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.projet_moodlets.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Button btnTravaux, btnQuiz;

    private Button btnCours;


    private ActivityResultLauncher<Intent> travauxLauncher;

    private BottomNavigationView menu;

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
        btnQuiz = findViewById(R.id.btn_Quiz);

        menu = findViewById(R.id.menu_navigation);


        ViewCompat.setOnApplyWindowInsetsListener(menu, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });



        btnTravaux.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(MainActivity.this, MesTravauxActivity.class);
                travauxLauncher.launch(intention);
                finish();
            }
        });

        btnQuiz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(MainActivity.this, MesQuizActivity.class);
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
    }
}