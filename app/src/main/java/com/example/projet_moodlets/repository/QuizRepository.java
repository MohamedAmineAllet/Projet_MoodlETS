package com.example.projet_moodlets.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.projet_moodlets.modele.daos.Quiz.HttpJsonQuizDao;
import com.example.projet_moodlets.modele.daos.Quiz.QuizLocalDao;
import com.example.projet_moodlets.modele.entites.Quiz;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class QuizRepository {

    private final Context context;
    private final MutableLiveData<List<Quiz>> quizzesLiveData = new MutableLiveData<>();

    public QuizRepository(Context context) {
        this.context = context;
    }

    public MutableLiveData<List<Quiz>> getQuizzes(){
        new Thread(() ->{
            try{
                HttpJsonQuizDao daoQuiz = new HttpJsonQuizDao();
                List<Quiz> liste = daoQuiz.getQuiz();
                quizzesLiveData.postValue(liste);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        return quizzesLiveData;
    }

    public void sauvegarderResultatLocal(Quiz quiz) throws JSONException, IOException {
        QuizLocalDao daoLocal = new QuizLocalDao(context);
        daoLocal.modifier(quiz);
    }
}
