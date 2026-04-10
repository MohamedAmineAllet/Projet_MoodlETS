package com.example.projet_moodlets.activities;

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

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.adapteurs.QuizAdapter;
import com.example.projet_moodlets.daos.quiz.QuizDaoSingleton;
import com.example.projet_moodlets.entites.Quiz;

import java.io.IOException;
import java.util.List;

public class MesQuizActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv;
    private QuizAdapter adapteur;
    private TextView btnTousQuiz, btnNonCommence, btnTermine;
    private EditText editTextRecherche;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_quiz);

        lv = findViewById(R.id.lvQuiz);
        btnTousQuiz = findViewById(R.id.txt_filtre_quiz);
        btnNonCommence = findViewById(R.id.txt_filtre_non_commence);
        btnTermine = findViewById(R.id.txt_filtre_termine);
        editTextRecherche = findViewById(R.id.edit_txt_recherche_quiz);

        View.OnClickListener ecouteurFiltres = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onFiltreClick(v);
            }
        };

        btnTousQuiz.setOnClickListener(ecouteurFiltres);
        btnNonCommence.setOnClickListener(ecouteurFiltres);
        btnTermine.setOnClickListener(ecouteurFiltres);

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
                obtenirQuiz();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            Toast.makeText(this, "Non permis!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        Intent iQuizDetails = new Intent(this, QuizDetailsActivity.class);
        Quiz quizClique = (Quiz) adapterView.getAdapter().getItem(i);
        String idQuizClique = String.valueOf(quizClique.getId());
        iQuizDetails.putExtra("ID_QUIZ", idQuizClique);
        startActivity(iQuizDetails);
    }

    private void obtenirQuiz() throws IOException{
        new Thread(){
            @Override
            public void run(){
                List<Quiz> quiz = QuizDaoSingleton.getInstance().getQuiz();
                MesQuizActivity.this.runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        adapteur = new QuizAdapter(MesQuizActivity.this, R.layout.list_quiz, quiz);
                        lv.setAdapter(adapteur);
                    }
                });
            }
        }.start();
    }

   private void onFiltreClick(View view){
        TextView[] tousFiltres = {btnTousQuiz, btnNonCommence, btnTermine};

        TextView tvActif = (TextView) view;
        String filtre = tvActif.getText().toString();
        for(TextView tv: tousFiltres){
            tv.setBackgroundResource(R.drawable.filtre_quiz_ferme);
            tv.setTextColor(Color.parseColor("#B3212022"));
        }

        view.setBackgroundResource(R.drawable.filtre_quiz_active);

        adapteur.filtrer(filtre);

   }
}
