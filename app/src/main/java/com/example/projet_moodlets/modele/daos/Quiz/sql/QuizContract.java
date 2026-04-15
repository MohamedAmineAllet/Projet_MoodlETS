package com.example.projet_moodlets.modele.daos.Quiz.sql;

import android.provider.BaseColumns;

/**
 * Définit la structure de la base de données (noms des tables et colonnes).
 */
public class QuizContract {

    // Configuration de la base de données
    public static final String DB_NAME = "MOODLE.DB";
    public static final int DB_VERSION = 2;
    public static final String TABLE_NAME = "quizzes";

    /**
     * Noms des colonnes de la table quizzes.
     */
    public class Colonnes {
        public static final String ID = BaseColumns._ID; // ID unique géré par SQLite
        public static final String TITLE = "TITLE";
        public static final String STATUS = "STATUS";
        public static final String GRADE = "GRADE";
        public static final String TOTAL_POINTS = "TOTAL_POINTS";
        public static final String SUBMISSION_DATE = "SUBMISSION_DATE";
    }
}
