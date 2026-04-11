package com.example.projet_moodlets.modele.daos.Quiz;

public class QuizDaoSingleton {

    private static QuizDao daoInstance = null;

    public static QuizDao getInstance() {
        if (daoInstance==null)
            daoInstance = new HttpJsonQuizDao();
        return daoInstance;
    }
}
