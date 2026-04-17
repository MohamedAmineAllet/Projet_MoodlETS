package com.example.projet_moodlets.vue;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projet_moodlets.R;

import com.example.projet_moodlets.modele.daos.Utilisateur.HttpsJsonUtilisateurDao;
import com.example.projet_moodlets.vue.adapteurs.InscriptionPageAdapter;
import com.example.projet_moodlets.modele.entites.Utilisateur;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InscriptionActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TextView tvEtapeLabel;
    private Utilisateur userInscription = new Utilisateur();

    private final String[] labels = {
            "1/3  Mes informations générales",
            "2/3  Mes informations personnelles",
            "3/3  Ma photo de profil"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        viewPager    = findViewById(R.id.viewPager);
        tvEtapeLabel = findViewById(R.id.tvEtapeLabel);

        viewPager.setAdapter(new InscriptionPageAdapter(this));
        viewPager.setUserInputEnabled(false); // navigation par boutons uniquement
    }

    public void allerAEtape(int position) {
        viewPager.setCurrentItem(position, true);
        tvEtapeLabel.setText(labels[position]);
    }

    /**
     * Cette méthode permet d'ajouter un utilisateur à la base de donnée qui est le JSON serveur.
     */
    public void soumettre() {
        new Thread(() -> {
            try {
                HttpsJsonUtilisateurDao dao = new HttpsJsonUtilisateurDao();

                // Vérifier si le courriel existe déjà
                Utilisateur existant = dao.getUtilisateurParEmailEtPassword(
                        userInscription.getEmail(), "");

                // Chercher par email seulement
                List<Utilisateur> tous = dao.getUtilisateurs();
                boolean emailPris = false;
                for (Utilisateur u : tous) {
                    if (u.getEmail().equalsIgnoreCase(userInscription.getEmail())) {
                        emailPris = true;
                        break;
                    }
                }

                if (emailPris) {
                    runOnUiThread(() ->
                            Toast.makeText(this,
                                    "Ce courriel est déjà utilisé",
                                    Toast.LENGTH_SHORT).show());
                    return;
                }

                // Créer le nouvel utilisateur
                Utilisateur nouvelUtilisateur = new Utilisateur();
                nouvelUtilisateur.setUsername(userInscription.getPrenom());
                nouvelUtilisateur.setEmail(userInscription.getEmail());
                nouvelUtilisateur.setPassword(userInscription.getPassword());
                nouvelUtilisateur.setNom(userInscription.getNom());
                nouvelUtilisateur.setPrenom(userInscription.getPrenom());
                nouvelUtilisateur.setTelephone(userInscription.getTelephone());
                nouvelUtilisateur.setPhotoUrl(userInscription.getPhotoUrl());
                nouvelUtilisateur.setEnrolledCourseIds(new ArrayList<>());
                nouvelUtilisateur.setQuizResults(new ArrayList<>());
                nouvelUtilisateur.setCompletedAssignmentIds(new ArrayList<>());

                // Ajouter au JSON Server
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                JSONObject obj = new JSONObject();
                obj.put("username",               nouvelUtilisateur.getUsername());
                obj.put("email",                  nouvelUtilisateur.getEmail());
                obj.put("password",               nouvelUtilisateur.getPassword());
                obj.put("nom",                    nouvelUtilisateur.getNom());
                obj.put("prenom",                 nouvelUtilisateur.getPrenom());
                obj.put("telephone",              nouvelUtilisateur.getTelephone());
                obj.put("photoUrl",               nouvelUtilisateur.getPhotoUrl());
                obj.put("enrolledCourseIds",      new JSONArray());
                obj.put("quizResults",            new JSONArray());
                obj.put("completedAssignmentIds", new JSONArray());

                RequestBody body = RequestBody.create(obj.toString(), JSON);
                Request requete = new Request.Builder()
                        .url("http://10.0.2.2:3000/users")
                        .post(body)
                        .build();

                Response reponse = client.newCall(requete).execute();

                runOnUiThread(() -> {
                    if (reponse.isSuccessful()) {
                        Toast.makeText(this,
                                "Inscription réussie !",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, ConnexionActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this,
                                "Erreur lors de l'inscription",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(this,
                                "Impossible de joindre le serveur",
                                Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public Utilisateur getUserInscription() {
        return userInscription;
    }
}