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

public class ViewModelQuiz extends ViewModel {

    private QuizRepository repository;

    private final MutableLiveData<List<Quiz>> quizzesLiveData = new MutableLiveData<>();

    public void initRepository(Context context){
        if(repository == null){
            repository = new QuizRepository(context);
        }
    }

    public void chagerQuiz(){
        LiveData<List<Quiz>> result = repository.getQuizzes();

        if(result != null){
            result.observeForever(quizzes -> {
                if(quizzes != null){
                    quizzesLiveData.setValue(quizzes);
                }
            });
        }
    }

    public LiveData<List<Quiz>> getQuizLiveData(){
        return quizzesLiveData;
    }

    public void sauvegarderNote(Quiz quiz) throws JSONException, IOException {
        if ( repository != null){
            repository.sauvegarderResultatLocal(quiz);
        }
    }
}
