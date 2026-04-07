package com.example.projet_moodlets.adapteurs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.projet_moodlets.R;
import com.example.projet_moodlets.entites.Travail;

import java.util.ArrayList;
import java.util.List;

public class TravauxAdapter extends ArrayAdapter<Travail> {

    private List<Travail> lesTravaux;

    private Context contexte;

    private int viewResourceId;

    private Resources ressources;

    private TextView txtTitle, txtDate, txtCours, txtStatut,txtNote, txtScore;

    private LinearLayout llvTravail;
    public TravauxAdapter(@NonNull Context contexte, int viewResourceId, @NonNull List<Travail> travaux) {
        super(contexte, viewResourceId, new ArrayList<>(travaux));

        this.contexte = contexte;
        this.viewResourceId = viewResourceId;
        this.ressources = contexte.getResources();

        this.lesTravaux =  new ArrayList<>(travaux);
    }
/*
    @Override
    public int getCount() {
        return this.lesTravaux.size();
    }

    @Override
    public Travail getItem(int position){
        return lesTravaux.get(position);
    }*/

    @SuppressLint("NewApi")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) contexte.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.viewResourceId, parent, false);
        }

        final Travail travail = getItem(position);

        if (travail != null) {
            txtTitle = view.findViewById(R.id.txt_nom_travail);
            txtDate = view.findViewById(R.id.txt_date_echeance_travail);
            txtStatut = view.findViewById(R.id.txt_filtre_travail);
            txtCours = view.findViewById(R.id.txt_cours_travail);
            txtNote = view.findViewById(R.id.txt_note_travail);
            txtScore = view.findViewById(R.id.txt_Score_Pourcentage);



            String nomCours = "";
            switch(travail.getCourseId()){
                case 1 :
                    nomCours = "Applications mobiles";
                    break;
                case 2 :
                    nomCours = "Bases de données";
                    break;
                case 3 :
                    nomCours = "Programmation Web";
                    break;
                case 4 :
                    nomCours = "Projet intégrateur";
                    break;
                case 5 :
                    nomCours = "Programmation orienté";
                    break;
                case 6 :
                    nomCours = "Mathématiques discrètes";
                    break;
            }

            if(travail.getGrade() == null){
                txtNote.setVisibility(View.GONE);
                txtScore.setVisibility(View.GONE);
            }else{
                Double score = (travail.getGrade() * 100) / travail.getTotalPoints();
                txtScore.setText(score.toString() + "%");
            }

            txtTitle.setText(travail.getTitle());
            txtDate.setText(travail.getDueDate());
            txtStatut.setText(travail.getStatus());
            txtCours.setText(nomCours);



        }

        return view;
    }

    public void filtrer(String filtre){
        this.clear();

        if (filtre.equalsIgnoreCase("Tous les travaux")) {
            this.addAll(lesTravaux);
        } else {
            for (Travail t : lesTravaux) {
                if (t.getStatus().equalsIgnoreCase(filtre)) {
                    this.add(t);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void rechercher(String recherche){
        this.clear();

        if(recherche.isEmpty()){
            this.addAll(lesTravaux);
        }else{
            String query = recherche.toLowerCase().trim();
            for(Travail t: lesTravaux){
                String titre = t.getTitle().toLowerCase();
                //Code?

                if(titre.contains(query)){
                    this.add(t);
                }
            }
        }
        notifyDataSetChanged();
    }

}
