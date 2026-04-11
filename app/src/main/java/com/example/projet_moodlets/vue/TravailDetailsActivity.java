package com.example.projet_moodlets.vue;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.modele.daos.Cours.CoursDaoSingleton;
import com.example.projet_moodlets.modele.daos.Travail.TravauxDaoSingleton;
import com.example.projet_moodlets.modele.entites.Travail;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import java.io.IOException;
import java.time.LocalDate;

public class TravailDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnRetour;
    private Button btnRemise;

    private TextView txtTitle,txtCours, txtStatut, txtType, txtDate, txtDescription, txtInstructions, txtNote, txtVotreNote, txtLabelCommentaire, txtNomCommentaire, txtCommentaire, txtDateRemis;

    private Travail travail;

    private ProgressBar progressBar;

    private ConstraintLayout clNote;
    private LinearLayout llCommentaire, llRemise, llUrl, llMessage;

    private BottomNavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travail_details);

        btnRetour = findViewById(R.id.btnRondFlecheGauche_Travail_details);
        btnRemise = findViewById(R.id.btn_Remise);

        txtTitle = findViewById(R.id.txt_Nom_Travail_Details);
        txtCours = findViewById(R.id.txt_Cours_Travail_Details);
        txtStatut = findViewById(R.id.txt_statut_travail_details);
        txtType = findViewById(R.id.txt_type_travail_details);
        txtDate = findViewById(R.id.txt_Date_Limite_Travail_Details);
        txtDescription = findViewById(R.id.txt_Description_Travail_Details);
        txtInstructions = findViewById(R.id.txt_Instructions_Travaux_Details);
        txtNote = findViewById(R.id.txt_note_resultat_travail);
        txtVotreNote = findViewById(R.id.txt_Votre_Note);
        txtLabelCommentaire = findViewById(R.id.txt_Vos_Commentaires);
        txtDateRemis = findViewById(R.id.txt_Date_Remise);
        txtNomCommentaire = findViewById(R.id.txt_Correction_Titre);
        txtCommentaire = findViewById(R.id.txt_Commentaire);
        llUrl = findViewById(R.id.ll_Url);
        llMessage = findViewById(R.id.ll_Message);


        progressBar = findViewById(R.id.progressBar_note_travail_details);

        clNote = findViewById(R.id.cl_Note_Travail_details);
        llCommentaire = findViewById(R.id.ll_Commentaire);
        llRemise = findViewById(R.id.ll_Remise_Infos);


        btnRetour.setOnClickListener(this);
        btnRemise.setOnClickListener(this);

        Intent intent = getIntent();
        String idTravail = intent.getStringExtra("ID_TRAVAIL");
        try{
            obtenirTravail(idTravail);
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

            if (id == R.id.travaux) {
                return true;
            }

            if(id == R.id.cours){
                Intent iMesCours = new Intent(this, MesCoursActivity.class);
                iMesCours.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iMesCours);
                overridePendingTransition(0, 0);
                return true;
            }else if(id == R.id.dashboard){
                Intent iDashboard = new Intent(this, MainActivity.class);
                iDashboard.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(iDashboard);
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
    }

    @Override
    public void onClick(View view) {
        if(view == btnRetour){
            Intent iTravaux = new Intent(this, MesTravauxActivity.class);
            startActivity(iTravaux);
        }

        if (view == btnRemise){
            travail.setStatus("Remis");
            travail.setSubmissionDate(LocalDate.now().toString());
            new Thread() {
                @Override
                public void run() {

                    try {
                        TravauxDaoSingleton.getInstance().modifier(travail);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    runOnUiThread(() -> {
                        Toast.makeText(TravailDetailsActivity.this, "Travail remis", Toast.LENGTH_SHORT).show();
                        obtenirTravail(String.valueOf(travail.getId()));
                    });
                }
            }.start();
        }
    }

    public void obtenirTravail(String id){
        new Thread(){
            @Override
            public void run(){
                travail = TravauxDaoSingleton.getInstance().getTravailParId(id);
               TravailDetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtTitle.setText(travail.getTitle());
                        txtStatut.setText(travail.getStatus());
                        txtType.setText(travail.getType());
                        txtDate.setText(travail.getDueDate());
                        txtDescription.setText(travail.getDescription());
                        txtInstructions.setText(travail.getInstructions());

                        String titreCours = CoursDaoSingleton.getInstance().getTitreParId(String.valueOf(travail.getCourseId()));
                        txtCours.setText(titreCours);


                        if (travail.getStatus().equals("Remis") || travail.getStatus().equals("Corrigé") && travail.getSubmissionDate() != null) {
                            llRemise.setVisibility(View.VISIBLE);
                            txtDateRemis.setText(travail.getSubmissionDate());

                            clNote.setVisibility(View.VISIBLE);
                            txtVotreNote.setVisibility(View.VISIBLE);
                            llUrl.setVisibility(View.GONE);
                            llMessage.setVisibility(View.GONE);
                            btnRemise.setVisibility(View.GONE);
                            if(travail.getStatus().equals("Corrigé")){
                                int note = (int) ((travail.getGrade() * 100) / travail.getTotalPoints());
                                txtNote.setText(note + " %");
                                progressBar.setProgress(note);
                                progressBar.setProgressTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#46AAA2")));
                                if(travail.getComment() != null){
                                    llCommentaire.setVisibility(View.VISIBLE);
                                    txtLabelCommentaire.setVisibility(View.VISIBLE);
                                    txtNomCommentaire.setText("Correction " + travail.getTitle());
                                    txtCommentaire.setText(travail.getComment());
                                }
                            }

                        } else {
                            llRemise.setVisibility(View.GONE);

                            clNote.setVisibility(View.GONE);
                            txtVotreNote.setVisibility(View.GONE);
                        }


                    }
                });
            }
        }.start();
    }

    @Override
    protected void onResume(){
        super.onResume();
        menu.setSelectedItemId(R.id.travaux);

    }
}
