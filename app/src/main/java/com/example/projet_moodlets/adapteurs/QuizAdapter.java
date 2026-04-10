package com.example.projet_moodlets.adapteurs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.entites.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizAdapter extends ArrayAdapter<Quiz> {

    private List<Quiz> lesQuiz;
    private Context contexte;

    private int viewResourceId;

    private Resources ressources;

    private TextView txtTitle, txtDate, txtCours, txtStatut,txtNote, txtScore;

    public QuizAdapter(@NonNull Context context, int viewResourceId, @NonNull List<Quiz> quiz) {
        super(context, viewResourceId, new ArrayList<>(quiz));

        this.contexte = context;
        this.viewResourceId = viewResourceId;
        this.ressources = contexte.getResources();

        this.lesQuiz =  new ArrayList<>(quiz);
    }

    @SuppressLint("NewApi")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;

        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) contexte.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.viewResourceId, parent, false);
        }

        final Quiz quiz = getItem(position);

        if(quiz != null){
            txtTitle = view.findViewById(R.id.txt_nom_quiz);
            txtDate = view.findViewById(R.id.txt_date_echeance_quiz);
            txtStatut = view.findViewById(R.id.txt_filtre_quiz);
            txtCours = view.findViewById(R.id.txt_cours_quiz);
            txtNote = view.findViewById(R.id.txt_note_quiz);
            txtScore = view.findViewById(R.id.txt_Score_Pourcentage_quiz);

            //À modifier
            String nomCours = "";
            switch(quiz.getCourseId()){
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

            if(quiz.getGrade() != null ){
                Double score = (quiz.getGrade() * 100) / quiz.getTotalPoints();
                txtScore.setText(score.toString() + "%");

                txtNote.setVisibility(View.VISIBLE);
                txtScore.setVisibility(View.VISIBLE);
            }else{
                txtNote.setVisibility(View.GONE);
                txtScore.setVisibility(View.GONE);
            }

            txtTitle.setText(quiz.getTitle());
            txtDate.setText(quiz.getDueDate());
            txtStatut.setText(quiz.getStatus());
            txtCours.setText(nomCours);
        }return view;
    }


    public void filtrer(String filtre){
        this.clear();

        if(filtre.equalsIgnoreCase("Tous les quiz")){
            this.addAll(lesQuiz);
        }else{
            for(Quiz q : lesQuiz){
                if(q.getStatus().equalsIgnoreCase(filtre)){
                    this.add(q);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void rechercher(String rechercher){
        this.clear();

        if(rechercher.isEmpty()){
            this.addAll(lesQuiz);
        }else{
            String query = rechercher.toLowerCase().trim();
            for(Quiz q: lesQuiz){
                String titre = q.getTitle().toLowerCase();

                if(titre.contains(query)){
                    this.add(q);
                }
            }
        }
        notifyDataSetChanged();
    }
}
