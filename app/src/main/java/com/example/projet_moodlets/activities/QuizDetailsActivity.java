package com.example.projet_moodlets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.daos.quiz.QuizDaoSingleton;
import com.example.projet_moodlets.entites.Quiz;

public class QuizDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnRetour;

    private TextView txtTitle, txtCours, txtStatut, txtDate, txtDateRemise, txtNbrQuestions, txtNote;

    private Quiz quiz;

    private ProgressBar progressBar;

    private ConstraintLayout clNote;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_details);

        btnRetour = findViewById(R.id.btnRondFlecheGauche_Quiz_details);

        txtTitle = findViewById(R.id.txt_Nom_Quiz_Details);
        txtCours = findViewById(R.id.txt_Cours_Quiz_Details);
        txtStatut = findViewById(R.id.txt_statut_quiz_details);
        txtDate = findViewById(R.id.txt_Date_Limite_Quiz_Details);
        txtDateRemise = findViewById(R.id.txt_Date_Remise_Quiz_Details);
        txtNbrQuestions = findViewById(R.id.txt_Nombre_Questions_Quiz_Details);
        txtNote = findViewById(R.id.txt_note_resultat_quiz);


        progressBar = findViewById(R.id.progressBar_note_quiz_details);

        clNote = findViewById(R.id.cl_Note_Quiz_details);


        btnRetour.setOnClickListener(this);

        Intent intent = getIntent();
        String idQuiz = intent.getStringExtra("ID_QUIZ");
        try{
            obtenirQuiz(idQuiz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onClick(View view) {
        if(view == btnRetour){
            Intent iTravaux = new Intent(this, MesTravauxActivity.class);
            startActivity(iTravaux);
        }
    }

    public void obtenirQuiz(String id){
        new Thread(){
            @Override
            public void run(){
                quiz = QuizDaoSingleton.getInstance().getQuizParId(Integer.parseInt(id));
                QuizDetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtTitle.setText(quiz.getTitle());
                        txtCours.setText(String.valueOf(quiz.getCourseId()));
                        txtStatut.setText(quiz.getStatus());
                        txtDate.setText(quiz.getDueDate());
                        txtDateRemise.setText(quiz.getSubmissionDate());
                        int nombreDeQuestions = quiz.getQuestions().size();
                        txtNbrQuestions.setText(nombreDeQuestions + " questions");
                        int note = (int) ((quiz.getGrade() * 100) / quiz.getTotalPoints());
                        txtNote.setText(note + " %");
                        progressBar.setProgress(note);
                        progressBar.setProgressTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#46AAA2")));
                    }
                });

            }
        }.start();
    }
}
