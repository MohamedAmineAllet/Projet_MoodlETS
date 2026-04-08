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
import com.example.projet_moodlets.entites.Cours;
import com.example.projet_moodlets.entites.Travail;

import java.util.ArrayList;
import java.util.List;

public class CoursAdapter extends ArrayAdapter<Cours> {
    private List<Cours> lesCours;

    private Context contexte;

    private int viewResourceId;

    private Resources ressources;

    private TextView ls_code_cours, ls_nom_cours, ls_session_cours;


    public CoursAdapter(@NonNull Context contexte, int viewResourceId, @NonNull List<Cours> Cours) {
        super(contexte, viewResourceId, new ArrayList<>(Cours));
        this.contexte = contexte;
        this.viewResourceId = viewResourceId;
        this.ressources = contexte.getResources();

        this.lesCours =  new ArrayList<>(Cours);
    }

    @SuppressLint("NewApi")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        // Optimisation du gonflage du layout
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) contexte.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.viewResourceId, parent, false);
        }

        // Récupération de l'objet Cours (et non Travail)
        final Cours cours = getItem(position);

        if (cours != null) {
            // Liaison avec les TextView de ton fichier XML "forme_list_cours"
            TextView ls_code_cours = view.findViewById(R.id.ls_code_cours);
            TextView ls_nom_cours = view.findViewById(R.id.ls_nom_cours);
            TextView ls_session_cours = view.findViewById(R.id.ls_session_cours);

            // Injection des données
            ls_code_cours.setText(cours.getCodeCours());
            ls_nom_cours.setText(cours.getNomCours());
            ls_session_cours.setText(cours.getSession());
        }

        return view;
    }
}
