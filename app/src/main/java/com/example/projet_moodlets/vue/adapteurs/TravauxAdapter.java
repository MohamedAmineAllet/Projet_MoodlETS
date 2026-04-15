package com.example.projet_moodlets.vue.adapteurs;

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
import com.example.projet_moodlets.modele.daos.Cours.CoursDao;
import com.example.projet_moodlets.modele.daos.Cours.CoursDaoSingleton;
import com.example.projet_moodlets.modele.entites.Cours;
import com.example.projet_moodlets.modele.entites.Travail;

import java.util.ArrayList;
import java.util.List;


/**
 * Adapteur pour l'affichage d'une liste de travaux (Assignments) dans un ListView.
 * Gère l'affichage dynamique des notes, le filtrage par statut et la recherche textuelle.
 */
public class TravauxAdapter extends ArrayAdapter<Travail> {

    private List<Travail> lesTravaux;
    private Context contexte;
    private int viewResourceId;
    private Resources ressources;

    // Composants de l'interface utilisateur pour chaque item
    private TextView txtTitle, txtDate, txtCours, txtStatut, txtNote, txtScore;
    private CoursDao coursDao;

    /**
     * Constructeur de l'adapteur.
     *
     * @param contexte       Contexte de l'application.
     * @param viewResourceId Identifiant du layout XML pour une ligne de la liste.
     * @param travaux        Liste initiale des travaux à afficher.
     */
    public TravauxAdapter(@NonNull Context contexte, int viewResourceId, @NonNull List<Travail> travaux) {
        super(contexte, viewResourceId, new ArrayList<>(travaux));
        this.contexte = contexte;
        this.viewResourceId = viewResourceId;
        this.ressources = contexte.getResources();
        this.lesTravaux = new ArrayList<>(travaux);
        this.coursDao = CoursDaoSingleton.getInstance();
    }

    /**
     * Construit la vue pour chaque ligne de la liste.
     *
     * @param position    Position de l'élément.
     * @param convertView Vue recyclée.
     * @param parent      Conteneur parent.
     * @return La vue d'item formatée avec les données du travail.
     */
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
            // Initialisation des vues
            txtTitle = view.findViewById(R.id.txt_nom_travail);
            txtDate = view.findViewById(R.id.txt_date_echeance_travail);
            txtStatut = view.findViewById(R.id.txt_filtre_travail);
            txtCours = view.findViewById(R.id.txt_cours_travail);
            txtNote = view.findViewById(R.id.txt_note_travail);
            txtScore = view.findViewById(R.id.txt_Score_Pourcentage);

            // Gestion de l'affichage de la note et du pourcentage
            if (travail.getGrade() != null) {
                Double score = (travail.getGrade() * 100) / travail.getTotalPoints();
                txtScore.setText(score.toString() + "%");

                txtNote.setVisibility(View.VISIBLE);
                txtScore.setVisibility(View.VISIBLE);
            } else {
                txtNote.setVisibility(View.GONE);
                txtScore.setVisibility(View.GONE);
            }

            // Affectation des valeurs textuelles
            txtTitle.setText(travail.getTitle());
            txtDate.setText(travail.getDueDate());
            txtStatut.setText(travail.getStatus());
            String titreCours = CoursDaoSingleton.getInstance().getTitreParId(String.valueOf(travail.getCourseId()));
            txtCours.setText(titreCours);
        }
        return view;
    }

    /**
     * Filtre la liste selon le statut sélectionné (ex: "Soumis", "En retard").
     *
     * @param filtre Le libellé du filtre ou "Tous les travaux".
     */
    public void filtrer(String filtre) {
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

    /**
     * Effectue une recherche par titre de travail, par nom de cours associé ou par le code du cours associé.
     *
     * @param recherche Texte saisi par l'utilisateur.
     */
    public void rechercher(String recherche) {
        this.clear();

        if (recherche.isEmpty()) {
            this.addAll(lesTravaux);
        } else {
            String query = recherche.toLowerCase().trim();
            for (Travail t : lesTravaux) {
                //On récupère le titre du travail
                String titre = t.getTitle().toLowerCase();

                //On cherche le cours correspondant dans la liste complète des cours
                String nomCours = "";
                String codeCours = "";

                List<Cours> tousLesCours = coursDao.getTousLesCours();
                if (tousLesCours != null) {
                    for (Cours c : tousLesCours) {
                        // Comparaison de l'ID (on convertit en String car ton entité Cours utilise String pour l'ID)
                        if (c.getId().equals(String.valueOf(t.getCourseId()))) {
                            nomCours = (c.getTitle() != null) ? c.getTitle().toLowerCase() : "";
                            codeCours = (c.getCode() != null) ? c.getCode().toLowerCase() : "";
                            break; // On a trouvé le cours, on sort de la boucle interne
                        }
                    }
                }

                //Vérification triple : Titre Travail OU Titre Cours OU Code Cours
                if (titre.contains(query) || nomCours.contains(query) || codeCours.contains(query)) {
                    this.add(t);
                }
            }
        }
        notifyDataSetChanged();
    }

}
