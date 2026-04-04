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
import com.example.projet_moodlets.modeles.Travail;

import java.util.List;

public class TravauxAdapter extends ArrayAdapter<Travail> {

    private List<Travail> lesTravaux;

    private Context contexte;

    private int viewResourceId;

    private Resources ressources;

    private TextView txtTitle, txtDate, txtCours, txtStatut;

    private LinearLayout llvTravail;
    public TravauxAdapter(@NonNull Context contexte, int viewResourceId, @NonNull List<Travail> travaux) {
        super(contexte, viewResourceId, travaux);

        this.contexte = contexte;
        this.viewResourceId = viewResourceId;
        this.ressources = contexte.getResources();
        this.lesTravaux = travaux;
    }

    @Override
    public int getCount() {
        return this.lesTravaux.size();
    }

    @SuppressLint("NewApi")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) contexte.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.viewResourceId, parent, false);
        }

        final Travail travail = this.lesTravaux.get(position);

        if (travail != null) {
            txtTitle = view.findViewById(R.id.txt_nom_travail);
            txtDate = view.findViewById(R.id.txt_date_echeance_travail);
            txtStatut = view.findViewById(R.id.txt_statut_travail);
            txtCours = view.findViewById(R.id.txt_cours_travail);



            txtTitle.setText(travail.getTitle());
            txtDate.setText(travail.getDueDate());
            txtStatut.setText(travail.getStatus());
            txtCours.setText(String.valueOf(travail.getCourseId()));



        }

        return view;
    }
}
