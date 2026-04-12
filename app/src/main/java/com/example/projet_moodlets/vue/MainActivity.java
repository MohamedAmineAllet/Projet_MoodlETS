package com.example.projet_moodlets.vue;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.projet_moodlets.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button btnTravaux, btnQuiz;

    private Button btnCours;
    private Button btnConnexion;


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
        btnConnexion = findViewById(R.id.btnConnexion);

        menu = findViewById(R.id.menu_navigation);

        ViewCompat.setOnApplyWindowInsetsListener(menu, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        menu.setOnItemSelectedListener(item ->{
            int id = item.getItemId();

            if (id == R.id.dashboard) {
                return true;
            }

            if(id == R.id.cours){
                Intent iMesCours = new Intent(this, MesCoursActivity.class);
                iMesCours.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iMesCours);
                overridePendingTransition(0, 0);
                return true;
            }else if(id == R.id.travaux){
                Intent iMesTravaux = new Intent(this, MesTravauxActivity.class);
                iMesTravaux.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iMesTravaux);
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



        btnTravaux.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(MainActivity.this, MesTravauxActivity.class);
                travauxLauncher.launch(intention);
            }
        });

        btnQuiz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(MainActivity.this, MesQuizActivity.class);
                travauxLauncher.launch(intention);
            }
        });
        btnCours.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intention = new Intent(MainActivity.this, MesCoursActivity.class);
                travauxLauncher.launch(intention);
            }
        });
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iConnexion = new Intent(MainActivity.this, ConnexionActivity.class);
                travauxLauncher.launch(iConnexion);
                finish();
            }
        });


    }
    @Override
    protected void onResume(){
        super.onResume();
        menu.setSelectedItemId(R.id.dashboard);

    }
}