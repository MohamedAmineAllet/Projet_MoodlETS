package com.example.projet_moodlets.modele.daos.Quiz.sql;

import android.provider.BaseColumns;

public class QuizContract {

    public static final String DB_NAME = "MOODLE.DB";

    public static final int DB_VERSION=2;
    public static final String TABLE_NAME="quizzes";

    public class Colonnes {
        public static final String ID= BaseColumns._ID;
        public static final String TITLE= "TITLE";
        public static final String STATUS= "STATUS";
        public static final String GRADE= "GRADE";
        public static final String TOTAL_POINTS= "TOTAL_POINTS";
        public static final String SUBMISSION_DATE= "SUBMISSION_DATE";
    }
}
