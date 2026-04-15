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
import com.example.projet_moodlets.modele.daos.Cours.CoursDaoSingleton;
import com.example.projet_moodlets.modele.entites.Quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapteur personnalisé pour afficher une liste d'objets Quiz dans un ListView ou un Spinner.
 * Gère l'affichage des scores, des dates et le filtrage dynamique.
 */
public class QuizAdapter extends ArrayAdapter<Quiz> {

    private List<Quiz> lesQuiz;
    private Context contexte;
    private int viewResourceId;
    private Resources ressources;

    // Références aux composants graphiques de la ligne (item)
    private TextView txtTitle, txtDate, txtCours, txtStatut, txtNote, txtScore;

    /**
     * Constructeur de l'adapteur.
     *
     * @param context        Contexte de l'application.
     * @param viewResourceId L'identifiant de la mise en page (layout) pour chaque ligne.
     * @param quiz           La liste initiale des quiz à afficher.
     */
    public QuizAdapter(@NonNull Context context, int viewResourceId, @NonNull List<Quiz> quiz) {
        super(context, viewResourceId, new ArrayList<>(quiz));

        this.contexte = context;
        this.viewResourceId = viewResourceId;
        this.ressources = contexte.getResources();

        this.lesQuiz = new ArrayList<>(quiz);
    }

    /**
     * Prépare et retourne la vue pour une ligne spécifique de la liste.
     *
     * @param position    Position de l'élément dans la liste.
     * @param convertView Vue recyclée (si disponible).
     * @param parent      Le parent auquel la vue sera attachée.
     * @return La vue remplie avec les données du quiz.
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

        final Quiz quiz = getItem(position);

        if (quiz != null) {
            // Liaison des composants UI
            txtTitle = view.findViewById(R.id.txt_nom_quiz);
            txtDate = view.findViewById(R.id.txt_date_echeance_quiz);
            txtStatut = view.findViewById(R.id.txt_filtre_quiz);
            txtCours = view.findViewById(R.id.txt_cours_quiz);
            txtNote = view.findViewById(R.id.txt_note_quiz);
            txtScore = view.findViewById(R.id.txt_Score_Pourcentage_quiz);


            // Gestion de l'affichage de la note (si disponible)
            if (quiz.getGrade() != null) {
                Double score = (quiz.getGrade() * 100) / quiz.getTotalPoints();
                txtScore.setText(score.toString() + "%");

                txtNote.setVisibility(View.VISIBLE);
                txtScore.setVisibility(View.VISIBLE);
            } else {
                txtNote.setVisibility(View.GONE);
                txtScore.setVisibility(View.GONE);
            }

            // Remplissage des données textuelles
            txtTitle.setText(quiz.getTitle());
            txtDate.setText(quiz.getDueDate());
            txtStatut.setText(quiz.getStatus());

            // Récupération du titre du cours via le Singleton
            String titreCours = CoursDaoSingleton.getInstance().getTitreParId(String.valueOf(quiz.getCourseId()));
            txtCours.setText(titreCours);


        }
        return view;
    }


    /**
     * Filtre la liste affichée selon le statut du quiz (ex: "Terminé", "À faire").
     *
     * @param filtre Le statut sélectionné ou "Tous les quiz".
     */
    public void filtrer(String filtre) {
        this.clear();

        if (filtre.equalsIgnoreCase("Tous les quiz")) {
            this.addAll(lesQuiz);
        } else {
            for (Quiz q : lesQuiz) {
                if (q.getStatus().equalsIgnoreCase(filtre)) {
                    this.add(q);
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Effectue une recherche textuelle sur les titres des quiz.
     *
     * @param rechercher La chaîne de caractères saisie par l'utilisateur.
     */
    public void rechercher(String rechercher) {
        this.clear();

        if (rechercher.isEmpty()) {
            this.addAll(lesQuiz);
        } else {
            String query = rechercher.toLowerCase().trim();
            for (Quiz q : lesQuiz) {
                // 1. Récupérer le titre du quiz
                String titre = q.getTitle().toLowerCase();
                // 2. Récupérer le nom du cours associé
                String nomCours = CoursDaoSingleton.getInstance().getTitreParId(String.valueOf(q.getCourseId())).toLowerCase();

                // Vérifier si la recherche correspond à l'un ou l'autre
                if (titre.contains(query) || nomCours.contains(query)) {
                    this.add(q);
                }
            }
        }
        notifyDataSetChanged();
    }
}
