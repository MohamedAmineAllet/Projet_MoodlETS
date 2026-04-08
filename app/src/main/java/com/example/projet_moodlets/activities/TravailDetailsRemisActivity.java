package com.example.projet_moodlets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.daos.TravauxDaoSingleton;
import com.example.projet_moodlets.entites.Travail;

public class TravailDetailsRemisActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnRetour;

    private TextView txtTitle, txtStatut, txtType, txtDate, txtDescription, txtInstructions, txtNote, txtVotreNote, txtCommentaire;

    private Travail travail;

    private ProgressBar progressBar;

    private ConstraintLayout clNote;
    private LinearLayout llCommentaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travail_details_remis);

        btnRetour = findViewById(R.id.btnRondFlecheGauche_Travail_details);
        txtTitle = findViewById(R.id.txt_Nom_Travail_Details);
        txtStatut = findViewById(R.id.txt_statut_travail_details);
        txtType = findViewById(R.id.txt_type_travail_details);
        txtDate = findViewById(R.id.txt_Date_Limite_Travail_Details);
        txtDescription = findViewById(R.id.txt_Description_Travail_Details);
        txtInstructions = findViewById(R.id.txt_Instructions_Travaux_Details);
        txtNote = findViewById(R.id.txt_note_resultat);
        txtVotreNote = findViewById(R.id.txt_Votre_Note);
        txtCommentaire = findViewById(R.id.txt_Vos_Commentaires);

        progressBar = findViewById(R.id.progressBar_note);

        clNote = findViewById(R.id.cl_Note);
        llCommentaire = findViewById(R.id.ll_Commentaire);


        btnRetour.setOnClickListener(this);

        Intent intent = getIntent();
        String idTravail = intent.getStringExtra("ID_TRAVAIL");
        try{
            obtenirTravail(idTravail);
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

    public void obtenirTravail(String id){
        new Thread(){
            @Override
            public void run(){
                travail = TravauxDaoSingleton.getInstance().getTravailParTitre(id);
               TravailDetailsRemisActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtTitle.setText(travail.getTitle());
                        txtStatut.setText(travail.getStatus());
                        txtType.setText(travail.getType());
                        txtDate.setText(travail.getDueDate());
                        txtDescription.setText(travail.getDescription());
                        txtInstructions.setText(travail.getInstructions());


                        if(travail.getStatus().equals("Corrigé")){
                            int note = (int) ((travail.getGrade() * 100) / travail.getTotalPoints());
                            txtNote.setText(note + " %");
                            progressBar.setProgress(note);
                        }else{
                            clNote.setVisibility(View.GONE);
                            txtVotreNote.setVisibility(View.GONE);
                        }
                        if(travail.getComment() == null){
                            llCommentaire.setVisibility(View.GONE);
                            txtCommentaire.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }.start();
    }
}
