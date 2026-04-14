package com.example.projet_moodlets.sqlite;

import android.provider.BaseColumns;

public class QuizResultContract {
    public static final String TABLE_NAME = "quiz_results";

    public static class Colonnes {
        public static final String ID = BaseColumns._ID;
        public static final String USER_ID = "userId";     // référence au user du JSON
        public static final String QUIZ_ID = "quizId";     // référence au quiz du JSON
        public static final String SCORE = "score";
        public static final String TOTAL = "total";
        public static final String DATE = "date";          // quand le quiz a été fait
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    Colonnes.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Colonnes.USER_ID + " TEXT, " +
                    Colonnes.QUIZ_ID + " TEXT, " +
                    Colonnes.SCORE + " INTEGER, " +
                    Colonnes.TOTAL + " INTEGER, " +
                    Colonnes.DATE + " TEXT)";

}
