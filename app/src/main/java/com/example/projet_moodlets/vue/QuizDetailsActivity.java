package com.example.projet_moodlets.vue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.modele.daos.Cours.CoursDaoSingleton;
import com.example.projet_moodlets.modele.daos.Cours.HttpJsonCoursDao;
import com.example.projet_moodlets.modele.daos.Quiz.QuizDaoSingleton;
import com.example.projet_moodlets.modele.daos.Quiz.QuizLocalDao;
import com.example.projet_moodlets.modele.entites.Cours;
import com.example.projet_moodlets.modele.entites.Quiz;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class QuizDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnRetour;
    private Button btnCommence;

    private TextView txtTitle, txtCours, txtStatut, txtDate, txtDateRemise, txtNbrQuestions, txtNote;

    private Quiz quiz;

    private ProgressBar progressBar;

    private ConstraintLayout clNote;

    private BottomNavigationView menu;


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
        btnCommence = findViewById(R.id.btn_commencer);


        progressBar = findViewById(R.id.progressBar_note_quiz_details);

        clNote = findViewById(R.id.cl_Note_Quiz_details);


        btnCommence.setOnClickListener(this);
        btnRetour.setOnClickListener(this);

        Intent intent = getIntent();
        String idQuiz = intent.getStringExtra("ID_QUIZ");
        try{
            obtenirQuiz(idQuiz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        menu = findViewById(R.id.menu_navigation);

        ViewCompat.setOnApplyWindowInsetsListener(menu, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        menu.setOnItemSelectedListener(item ->{
            int id = item.getItemId();

            if (id == R.id.quiz) {
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
            }else if(id == R.id.dashboard){
                Intent iDashboard = new Intent(this, MainActivity.class);
                iDashboard.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iDashboard);
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });

    }

    @Override
    public void onClick(View view) {
        if(view == btnRetour){
            Intent iQuiz = new Intent(this, MesQuizActivity.class);
            startActivity(iQuiz);
        }

        if(view == btnCommence){
            Intent iQuizTest = new Intent(this, QuizTestActivity.class);
            String idQuizClique = String.valueOf(quiz.getId());
            iQuizTest.putExtra("ID_QUIZ", idQuizClique);
            startActivity(iQuizTest);
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
                QuizLocalDao localDao = new QuizLocalDao(QuizDetailsActivity.this);
                localDao.sauvegarderResultatQuiz(quiz);
                QuizDetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        txtTitle.setText(quiz.getTitle());

                        String titreCours = CoursDaoSingleton.getInstance().getTitreParId(String.valueOf(quiz.getCourseId()));
                        txtCours.setText(titreCours);

                        txtStatut.setText(quiz.getStatus());
                        txtDate.setText(quiz.getDueDate());
                        txtDateRemise.setText(quiz.getSubmissionDate());

                        int nombreDeQuestions = quiz.getQuestions().size();
                        txtNbrQuestions.setText(nombreDeQuestions + " questions");

                        if(quiz.getStatus().equalsIgnoreCase("Terminé")){
                            btnCommence.setVisibility(View.GONE);
                            int note = (int) ((quiz.getGrade() * 100) / quiz.getTotalPoints());
                            txtNote.setText(note + " %");
                            progressBar.setProgress(note);
                            progressBar.setProgressTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#46AAA2")));
                        }else if(quiz.getStatus().equalsIgnoreCase("Non commencé")){
                            btnCommence.setVisibility(View.VISIBLE);
                        }

                    }
                });

            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        menu.setSelectedItemId(R.id.quiz);

        Intent intent = getIntent();
        String idQuiz = intent.getStringExtra("ID_QUIZ");

        if (idQuiz != null) {
            obtenirQuiz(idQuiz);
        }
    }
}
