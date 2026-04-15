package com.example.projet_moodlets.vue;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_moodlets.R;

public class MonProfileActivity extends AppCompatActivity {
    ImageView imageProfile;
    TextView textNomPrenom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_profile);
        imageProfile = findViewById(R.id.iv_profile);
        textNomPrenom = findViewById(R.id.profile_nom_prenom);

    }
}