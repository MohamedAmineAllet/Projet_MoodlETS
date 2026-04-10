package com.example.projet_moodlets.daos.quiz;

import com.example.projet_moodlets.entites.Quiz;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface QuizDao {

    List<String> getTitresDesQuiz();

    List<Quiz> getQuiz();

    Quiz getQuizParId(int id);

    void modifier(Quiz quiz) throws IOException, JSONException;

}
