package com.example.projet_moodlets.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projet_moodlets.modele.entites.Quiz;
import com.example.projet_moodlets.repository.QuizRepository;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * ViewModel gérant l'état de l'interface utilisateur pour la section Quiz.
 * Il survit aux changements de configuration (comme la rotation de l'écran).
 */
public class ViewModelQuiz extends ViewModel {

    private QuizRepository repository;
    private final MutableLiveData<List<Quiz>> quizzesLiveData = new MutableLiveData<>();

    /**
     * Initialise le repository si ce n'est pas déjà fait.
     *
     * @param context Contexte de l'application nécessaire pour la base de données SQLite.
     */
    public void initRepository(Context context) {
        if (repository == null) {
            repository = new QuizRepository(context);
        }
    }

    /**
     * Déclenche la récupération des quiz via le repository et observe le résultat.
     * Met à jour la donnée observable quizzesLiveData une fois les données reçues.
     */
    public void chagerQuiz() {
        LiveData<List<Quiz>> result = repository.getQuizzes();

        if (result != null) {
            result.observeForever(quizzes -> {
                if (quizzes != null) {
                    quizzesLiveData.setValue(quizzes);
                }
            });
        }
    }

    /**
     * @return Le LiveData que l'activité doit observer pour afficher la liste des quiz.
     */
    public LiveData<List<Quiz>> getQuizLiveData() {
        return quizzesLiveData;
    }

    /**
     * Demande au repository de persister la note du quiz localement.
     *
     * @param quiz Le quiz terminé contenant le score final.
     */
    public void sauvegarderNote(Quiz quiz) throws JSONException, IOException {
        if (repository != null) {
            repository.sauvegarderResultatLocal(quiz);
        }
    }
}
