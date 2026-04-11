package com.example.projet_moodlets.vue;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.modele.daos.Cours.CoursDaoSingleton;
import com.example.projet_moodlets.modele.daos.Cours.HttpJsonCoursDao;
import com.example.projet_moodlets.modele.daos.Quiz.QuizDaoSingleton;
import com.example.projet_moodlets.modele.daos.Quiz.sql.DbUtil;
import com.example.projet_moodlets.modele.daos.Quiz.sql.QuizContract;
import com.example.projet_moodlets.modele.entites.Cours;
import com.example.projet_moodlets.modele.entites.Question;
import com.example.projet_moodlets.modele.entites.Quiz;
import com.example.projet_moodlets.vue.adapteurs.QuizAdapter;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.List;

public class QuizTestActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btnRetour;
    private Button  btnChoix1, btnChoix2, btnChoix3, btnDerriere, btnProchain;

    private TextView txtTitle, txtCours, txtStatut, txtQuestion;

    private QuizAdapter adapteur;

    private Quiz quiz;

    private int questionActuelle = 0;
    private int[] reponses;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_quiz);

        btnRetour = findViewById(R.id.btnRondFlecheGauche_Quiz_test);

        btnChoix1 = findViewById(R.id.btn_choix_1);
        btnChoix2 = findViewById(R.id.btn_choix_2);
        btnChoix3 = findViewById(R.id.btn_choix_3);

        btnDerriere = findViewById(R.id.btn_retour);
        btnProchain = findViewById(R.id.btn_prochain);

        txtTitle = findViewById(R.id.txt_Nom_Quiz_test);
        txtCours = findViewById(R.id.txt_Cours_Quiz_test);
        txtStatut = findViewById(R.id.txt_statut_quiz_test);
        txtQuestion = findViewById(R.id.txt_Question);

        btnRetour.setOnClickListener(this);
        btnChoix1.setOnClickListener(this);
        btnChoix2.setOnClickListener(this);
        btnChoix3.setOnClickListener(this);
        btnDerriere.setOnClickListener(this);
        btnProchain.setOnClickListener(this);

        Intent intent = getIntent();
        String idQuiz = intent.getStringExtra("ID_QUIZ");
        try{
            obtenirQuiz(idQuiz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnChoix1){
            selectionnerBouton(btnChoix1, 0);

        }
        else if (v == btnChoix2) {
            selectionnerBouton(btnChoix2, 1);
        }
        else if (v == btnChoix3){
            selectionnerBouton(btnChoix3, 2);
        }

        if (v == btnProchain) {
            if (questionActuelle < quiz.getQuestions().size() - 1) {
                questionActuelle++;
                afficherQuestion();
                reinitialiserBordures();

            } else {
                sauvegarderResultat();
            }
        } else if (v == btnDerriere) {
            questionActuelle--;
            afficherQuestion();
        } else if (v == btnRetour) {
            finish();
        }
    }

    private void selectionnerBouton(View boutonSelectionne, int indexReponse) {
        reponses[questionActuelle] = indexReponse;

        Button[] boutons = {btnChoix1, btnChoix2, btnChoix3};

        for (Button b : boutons) {
            MaterialButton materialButton = (MaterialButton) b;
            if (b == boutonSelectionne) {
                materialButton.setStrokeWidth(5);
                materialButton.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#212022")));
            } else {
                materialButton.setStrokeWidth(2);
                materialButton.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#D5D3DF")));
            }
        }
    }
    private void reinitialiserBordures() {
        com.google.android.material.button.MaterialButton[] boutons = {
                (com.google.android.material.button.MaterialButton) btnChoix1,
                (com.google.android.material.button.MaterialButton) btnChoix2,
                (com.google.android.material.button.MaterialButton) btnChoix3
        };

        for (com.google.android.material.button.MaterialButton b : boutons) {
            b.setStrokeWidth(2);
            b.setStrokeColor(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#D5D3DF"))); // Gris clair
        }
    }

    public void obtenirQuiz(String id){
        new Thread(){
            @Override
            public void run(){
                if (CoursDaoSingleton.getInstance().getTousLesCours().isEmpty()) {
                    List<Cours> liste = new HttpJsonCoursDao().getTousLesCours();
                    CoursDaoSingleton.getInstance().remplirCache(liste);
                }
                quiz = QuizDaoSingleton.getInstance().getQuizParId(Integer.parseInt(id));

                QuizTestActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        txtTitle.setText(quiz.getTitle());

                        String titreCours = CoursDaoSingleton.getInstance().getTitreParId(String.valueOf(quiz.getCourseId()));
                        txtCours.setText(titreCours);

                        txtStatut.setText(quiz.getStatus());

                        reponses = new int[quiz.getQuestions().size()];
                        java.util.Arrays.fill(reponses, -1); // -1 signifie "pas encore répondu"
                        afficherQuestion();

                    }
                });

            }
        }.start();
    }

    private void afficherQuestion(){
        if(quiz != null && quiz.getQuestions() != null){
            Question question = quiz.getQuestions().get(questionActuelle);

            txtQuestion.setText(question.getQuestion());

            btnChoix1.setText(question.getOptions().get(0));
            btnChoix2.setText(question.getOptions().get(1));
            btnChoix3.setText(question.getOptions().get(2));

            if(questionActuelle == 0){
                btnDerriere.setVisibility(View.GONE);
            }else{
                btnDerriere.setVisibility(View.VISIBLE);
            }

            if (questionActuelle == quiz.getQuestions().size() - 1) {
                btnProchain.setText("Terminer");
            } else {
                btnProchain.setText("Prochain");
            }
        }
    }

    private void sauvegarderResultat(){
        int points =0;
        List<Question> questions = quiz.getQuestions();

        for (int i = 0; i < questions.size(); i++) {
            if (reponses[i] == questions.get(i).getCorrectOption()) {
                points++;
            }
        }

        DbUtil dbUtil = new DbUtil(this);
        SQLiteDatabase db = dbUtil.getWritableDatabase();


        android.content.ContentValues values = new android.content.ContentValues();

        values.put(QuizContract.Colonnes.ID, quiz.getId());
        values.put(QuizContract.Colonnes.TITLE, quiz.getTitle());
        values.put(QuizContract.Colonnes.STATUS, "Terminé");
        values.put(QuizContract.Colonnes.GRADE, (double) points);
        values.put(QuizContract.Colonnes.TOTAL_POINTS, (double) questions.size());
        values.put(QuizContract.Colonnes.SUBMISSION_DATE, "2026-04-11");

        // "REPLACE" met à jour si l'ID existe déjà, sinon il l'insère
        db.insertWithOnConflict(QuizContract.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

        finish(); // Retourner aux détails du quiz
    }
}
