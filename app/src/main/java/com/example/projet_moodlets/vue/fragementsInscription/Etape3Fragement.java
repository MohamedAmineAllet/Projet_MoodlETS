package com.example.projet_moodlets.vue.fragementsInscription;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.projet_moodlets.R;
import com.example.projet_moodlets.vue.InscriptionActivity;
import com.example.projet_moodlets.modele.entites.Utilisateur;

public class Etape3Fragement extends Fragment {

    private ImageView imgApercu;
    private TextView tvPhotoStatus;
    private Uri photoUri = null;

    private final ActivityResultLauncher<String> galerieLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            photoUri = uri;
                            imgApercu.setImageURI(uri);
                            tvPhotoStatus.setText("Photo sélectionnée ✓");
                            tvPhotoStatus.setTextColor(
                                    ContextCompat.getColor(requireContext(),
                                            android.R.color.holo_green_dark));
                        }
                    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inscription_etape3, container, false);

        imgApercu     = view.findViewById(R.id.imgApercu);
        tvPhotoStatus = view.findViewById(R.id.tvPhotoStatus);

        // Ouvre directement la galerie — sans demander de permission
        view.findViewById(R.id.btnGalerie).setOnClickListener(v ->
                galerieLauncher.launch("image/*"));

        view.findViewById(R.id.btnPrecedent).setOnClickListener(v ->
                ((InscriptionActivity) requireActivity()).allerAEtape(1));

        view.findViewById(R.id.btnConfirmer).setOnClickListener(v -> {
            Utilisateur user =
                    ((InscriptionActivity) requireActivity()).getUserInscription();
            user.setPhotoUrl(photoUri != null ? photoUri.toString() : "");
            ((InscriptionActivity) requireActivity()).soumettre();
        });

        return view;
    }
}