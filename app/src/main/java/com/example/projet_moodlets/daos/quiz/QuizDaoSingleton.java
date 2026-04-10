package com.example.projet_moodlets.daos.quiz;

import com.example.projet_moodlets.daos.Travail.HttpJsonTravauxDao;
import com.example.projet_moodlets.daos.Travail.TravauxDao;

public class QuizDaoSingleton {

    private static QuizDao daoInstance = null;

    public static QuizDao getInstance() {
        if (daoInstance==null)
            daoInstance = new HttpJsonQuizDao();
        return daoInstance;
    }
}
