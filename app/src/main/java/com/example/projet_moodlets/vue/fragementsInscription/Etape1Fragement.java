package com.example.projet_moodlets.vue.fragementsInscription;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;

import com.example.projet_moodlets.R;
import com.example.projet_moodlets.vue.InscriptionActivity;
import com.example.projet_moodlets.modele.entites.Utilisateur;

public class Etape1Fragement extends Fragment {

    private EditText etPrenom, etNom, etEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inscription_etape1, container, false);

        etPrenom = view.findViewById(R.id.etPrenom);
        etNom    = view.findViewById(R.id.etNom);
        etEmail  = view.findViewById(R.id.etEmail);

        view.findViewById(R.id.btnSuivant).setOnClickListener(v -> {
            if (valider()) {
                // Sauvegarder dans l'objet partagé
                Utilisateur user = ((InscriptionActivity) requireActivity()).getUserInscription();
                user.setPrenom(etPrenom.getText().toString().trim());
                user.setNom(etNom.getText().toString().trim());
                user.setEmail(etEmail.getText().toString().trim());
                // Aller à l'étape 2
                ((InscriptionActivity) requireActivity()).allerAEtape(1);
            }
        });

        return view;
    }

    private boolean valider() {
        boolean valide = true;
        if (etPrenom.getText().toString().trim().isEmpty()) {
            etPrenom.setError("Champ obligatoire");
            valide = false;
        }
        if (etNom.getText().toString().trim().isEmpty()) {
            etNom.setError("Champ obligatoire");
            valide = false;
        }
        if (etEmail.getText().toString().trim().isEmpty()) {
            etEmail.setError("Champ obligatoire");
            valide = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(etEmail.getText().toString().trim()).matches()) {
            etEmail.setError("Courriel invalide");
            valide = false;
        }
        return valide;
    }
}