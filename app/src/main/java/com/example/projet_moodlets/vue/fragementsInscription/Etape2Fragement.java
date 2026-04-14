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

public class Etape2Fragement extends Fragment {

    private EditText etTelephone, etPassword, etEmailConfirm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inscription_etape2, container, false);

        etTelephone   = view.findViewById(R.id.etTelephone);
        etPassword    = view.findViewById(R.id.etPassword);
        etEmailConfirm = view.findViewById(R.id.etEmailConfirm);

        view.findViewById(R.id.btnPrecedent).setOnClickListener(v ->
                ((InscriptionActivity) requireActivity()).allerAEtape(0));

        view.findViewById(R.id.btnSuivant).setOnClickListener(v -> {
            if (valider()) {
                Utilisateur user = ((InscriptionActivity) requireActivity()).getUserInscription();
                user.setTelephone(etTelephone.getText().toString().trim());
                user.setPassword(etPassword.getText().toString().trim());
                ((InscriptionActivity) requireActivity()).allerAEtape(2);
            }
        });

        return view;
    }

    private boolean valider() {
        boolean valide = true;
        if (etPassword.getText().toString().trim().isEmpty()) {
            etPassword.setError("Champ obligatoire");
            valide = false;
        } else if (etPassword.getText().toString().trim().length() < 6) {
            etPassword.setError("Minimum 6 caractères");
            valide = false;
        }
        return valide;
    }
}