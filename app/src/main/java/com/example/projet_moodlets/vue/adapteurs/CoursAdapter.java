package com.example.projet_moodlets.vue.adapteurs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.modele.entites.Cours;

import java.util.List;

public class CoursAdapter extends ArrayAdapter<Cours> {
    private List<Cours> lesCours;

    private Context contexte;

    private int viewResourceId;

    private Resources ressources;


    public CoursAdapter(@NonNull Context contexte, int viewResourceId, @NonNull List<Cours> Cours) {
        super(contexte, viewResourceId, Cours);
        this.contexte = contexte;
        this.viewResourceId = viewResourceId;
        this.ressources = contexte.getResources();

        this.lesCours =  Cours;
    }

    public void filtreListeCours(List<Cours> listeFiltree) {
        this.clear();
        if (listeFiltree != null) {
            this.addAll(listeFiltree);
        }
        notifyDataSetChanged();
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
            // Liaison avec les TextView du fichier XML "list_cours"
            TextView ls_code_cours = view.findViewById(R.id.ls_code_cours);
            TextView ls_nom_cours = view.findViewById(R.id.ls_nom_cours);
            TextView ls_session_cours = view.findViewById(R.id.ls_session_cours);
            ImageView icone_cours = view.findViewById(R.id.icone_cours);

            // Injection des données
            ls_code_cours.setText(cours.getCode());
            ls_nom_cours.setText(cours.getTitle());
            ls_session_cours.setText(cours.getSession());
            String nomImage = cours.getImageCours(); // Récupère par ex: "application_mobile_icone"

            if (nomImage != null && !nomImage.isEmpty()) {
                // On cherche l'ID de la ressource drawable par son nom
                int resId = contexte.getResources().getIdentifier(nomImage, "drawable", contexte.getPackageName());

                if (resId != 0) {
                    // setImageResource remplace le srcCompat (l'icône)
                    // tout en gardant le background (le squircle mauve) défini dans le XML
                    icone_cours.setImageResource(resId);
                } else {
                    // Image par défaut si le nom est introuvable
                    icone_cours.setImageResource(R.drawable.icone_web);
                }
            }
        }

        return view;
    }
}
