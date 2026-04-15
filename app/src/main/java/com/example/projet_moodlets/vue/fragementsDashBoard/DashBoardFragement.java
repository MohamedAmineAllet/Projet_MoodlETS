package com.example.projet_moodlets.vue.fragementsDashBoard;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.EtatConnexion.SessionManager;
import com.example.projet_moodlets.modele.daos.Cours.CoursDaoSingleton;
import com.example.projet_moodlets.modele.daos.Travail.TravauxDaoSingleton;
import com.example.projet_moodlets.modele.entites.Annonce;
import com.example.projet_moodlets.modele.entites.Cours;
import com.example.projet_moodlets.modele.entites.Travail;
import com.example.projet_moodlets.vue.adapteurs.CoursRecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashBoardFragement extends Fragment {

    private RecyclerView recyclerView;
    private CoursRecyclerAdapter adapter;
    private List<Cours> listeCours = new ArrayList<>();
    private LinearLayout containerNotifications;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragement_dashboard, container, false);

        SessionManager session = new SessionManager(getContext());
        TextView tvBonjour = v.findViewById(R.id.tv_bonjour);
        tvBonjour.setText("Bonjour, " + session.getPrenom());

        TextView tvDate = v.findViewById(R.id.tv_date);
        tvDate.setText(new SimpleDateFormat("d MMMM yyyy", Locale.FRENCH).format(new Date()));


        containerNotifications = v.findViewById(R.id.container_notifications);
        recyclerView = v.findViewById(R.id.recycler_cours);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new CoursRecyclerAdapter(getContext(), listeCours);
        recyclerView.setAdapter(adapter);

        chargerDonnees();

        return v;
    }

    /**
     * Cette méthode permet de récuperer toutes les données comme Cours/Travail etc de l'utilisateur
     * qui nous seront utile pour afficher soit dans les notifications ou pour les cours.
     */
    private void chargerDonnees() {
        new Thread(() -> {
            try {
                List<Cours> coursResult = CoursDaoSingleton.getInstance().getTousLesCours();
                List<Travail> travauxResult = TravauxDaoSingleton.getInstance().getTravaux();

                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        // Mise à jour du RecyclerView des cours
                        if (coursResult != null) {
                            listeCours.clear();
                            listeCours.addAll(coursResult);
                            adapter.notifyDataSetChanged();
                        }

                        // Génération des notifications (Annonces + Travaux)
                        genererNotifications(coursResult, travauxResult);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Cette méthode permet d'afficher tous les différentes notifications et
     * de gerer s'il s'agit d'une notification de type Travail/Quiz à completer ou remettre ou s'il s'agit juste d'une annonce.
     * @param cours C Il s'agit de la liste des cours de l'utilisateur connecté.
     * @param travaux il s'agit des travaux de l'utilisateur connecté
     */
    private void genererNotifications(List<Cours> cours, List<Travail> travaux) {
        if (containerNotifications == null) return;
        containerNotifications.removeAllViews();

        // 1. Affichage des Travaux/Quiz à remettre
        if (travaux != null) {
            for (Travail t : travaux) {
                // On affiche seulement si ce n'est pas terminé
                if (!"Remis".equalsIgnoreCase(t.getStatus()) && !"Corrigé".equalsIgnoreCase(t.getStatus())) {
                    ajouterLigneNotification(
                            "Vous avez un " + t.getTitle() + " à remettre",
                            "le " + t.getDueDate(),
                            R.drawable.icon_exclamation
                    );
                }
            }
        }

        // 2. Affichage des Annonces
        if (cours != null) {
            for (Cours c : cours) {
                if (c.getAnnonces() != null) {
                    for (Annonce a : c.getAnnonces()) {
                        ajouterLigneNotification(
                                "Nouvelle annonce : " + a.getTitre(),
                                "Publié par " + a.getAuteur(),
                                R.drawable.icon_exclamation
                        );
                    }
                }
            }
        }
    }

    /**
     * Cette méthode permet d'ajouter une notification en affichage 
     * pour afficher dynamiquement en fonction des travaux qui seront à remettre et des quiz à remettre.
     * @param titre il s'agit du titre de la notification
     * @param detail il s'agit des informations secondaires de la notificat
     * @param iconRes
     */
    private void ajouterLigneNotification(String titre, String detail, int iconRes) {
        View notifView = getLayoutInflater().inflate(R.layout.item_notification, containerNotifications, false);

        TextView tvMsg = notifView.findViewById(R.id.tv_notif_message);
        TextView tvDet = notifView.findViewById(R.id.tv_notif_details);
        ImageView icon = notifView.findViewById(R.id.img_notif_icon);

        tvMsg.setText(titre);
        tvDet.setText(detail);
        icon.setImageResource(iconRes);

        containerNotifications.addView(notifView);
    }
}