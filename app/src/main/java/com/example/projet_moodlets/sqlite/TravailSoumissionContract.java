package com.example.projet_moodlets.sqlite;


import android.provider.BaseColumns;

public class TravailSoumissionContract {
    public static final String TABLE_NAME = "submissions";

    public static class Colonnes {
        public static final String ID = BaseColumns._ID;
        public static final String USER_ID = "userId";
        public static final String ASSIGNMENT_ID = "assignmentId";
        public static final String STATUT = "statut";       // "Remis", "En retard"
        public static final String CONTENU = "contenu";     // texte ou URL soumis
        public static final String DATE_REMISE = "dateRemise";
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    Colonnes.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Colonnes.USER_ID + " TEXT, " +
                    Colonnes.ASSIGNMENT_ID + " TEXT, " +
                    Colonnes.STATUT + " TEXT, " +
                    Colonnes.CONTENU + " TEXT, " +
                    Colonnes.DATE_REMISE + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}