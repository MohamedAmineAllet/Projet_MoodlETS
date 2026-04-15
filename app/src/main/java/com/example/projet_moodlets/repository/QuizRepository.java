package com.example.projet_moodlets.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.projet_moodlets.modele.daos.Quiz.HttpJsonQuizDao;
import com.example.projet_moodlets.modele.daos.Quiz.QuizLocalDao;
import com.example.projet_moodlets.modele.entites.Quiz;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Repository gérant la logique d'accès aux données pour les Quiz.
 * Il sert d'intermédiaire entre le ViewModel et les différentes sources de données (API et SQLite).
 */
public class QuizRepository {

    private final Context context;
    private final MutableLiveData<List<Quiz>> quizzesLiveData = new MutableLiveData<>();

    public QuizRepository(Context context) {
        this.context = context;
    }

    /**
     * Récupère la liste des quiz depuis le serveur distant de manière asynchrone.
     * Met à jour le LiveData une fois la récupération terminée.
     *
     * @return Le MutableLiveData contenant la liste des quiz pour observation par la vue.
     */
    public MutableLiveData<List<Quiz>> getQuizzes() {
        new Thread(() -> {
            try {
                HttpJsonQuizDao daoQuiz = new HttpJsonQuizDao();
                List<Quiz> liste = daoQuiz.getQuiz();
                quizzesLiveData.postValue(liste);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        return quizzesLiveData;
    }

    /**
     * Enregistre les modifications d'un quiz (note, statut) dans la base de données locale.
     *
     * @param quiz L'objet quiz modifié à persister.
     * @throws JSONException En cas d'erreur de formatage.
     * @throws IOException   En cas d'erreur d'accès à la base de données.
     */
    public void sauvegarderResultatLocal(Quiz quiz) throws JSONException, IOException {
        QuizLocalDao daoLocal = new QuizLocalDao(context);
        daoLocal.modifier(quiz);
    }
}
