package com.example.projet_moodlets.daos.quiz;

import com.example.projet_moodlets.entites.Quiz;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizLocalDao implements QuizDao{

    private List<Quiz> quiz = new ArrayList<>();

    @Override
    public List<String> getTitresDesQuiz() {
        List<String> lesQuiz = new ArrayList<>();
        for(Quiz q: quiz){
            lesQuiz.add(q.getTitle());
        }
        return lesQuiz;
    }

    @Override
    public List<Quiz> getQuiz() {
        return quiz;
    }

    @Override
    public Quiz getQuizParId(int id) {
        for(Quiz q:quiz){
            if(q.getId() == id){
                return q;
            }
        }return null;
    }

    @Override
    public void modifier(Quiz quiz) throws IOException, JSONException {

    }
}
