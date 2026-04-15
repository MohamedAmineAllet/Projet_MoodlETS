package com.example.projet_moodlets.modele.daos.Quiz.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe utilitaire pour la gestion de la base de données SQLite locale.
 * Elle permet la création, l'ouverture et la mise à jour de la base de données des quiz.
 */
public class DbUtil extends SQLiteOpenHelper {

    /**
     * Constructeur de la base de données.
     *
     * @param context Le contexte de l'application (généralement l'activité).
     */
    public DbUtil(Context context) {
        // Appelle le constructeur parent avec le nom et la version définis dans le contrat
        super(context, QuizContract.DB_NAME, null, QuizContract.DB_VERSION);
    }

    /**
     * Appelé lors de la toute première création de la base de données.
     *
     * @param db L'instance de la base de données.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Préparation de la requête SQL de création de table en utilisant les constantes du contrat
        String requeteCreation = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s REAL, %s REAL, %s TEXT)",
                QuizContract.TABLE_NAME, // Nom de la table
                QuizContract.Colonnes.ID, // Clé primaire auto-incrémentée
                QuizContract.Colonnes.TITLE, // Titre du quiz (TEXT)
                QuizContract.Colonnes.STATUS, // État : Terminé ou Non commencé (TEXT)
                QuizContract.Colonnes.GRADE, // Note obtenue (REAL/Double)
                QuizContract.Colonnes.TOTAL_POINTS, // Points totaux possibles (REAL/Double)
                QuizContract.Colonnes.SUBMISSION_DATE // Date de remise enregistrée (TEXT)
        );
        // Exécution de la commande SQL pour créer physiquement la table sur le disque
        db.execSQL(requeteCreation);
    }

    /**
     * Appelé lorsqu'un changement de version (DATABASE_VERSION) est détecté.
     *
     * @param db         L'instance de la base de données.
     * @param oldVersion Ancienne version sur l'appareil.
     * @param newVersion Nouvelle version définie dans le code.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Stratégie de mise à jour par réinitialisation :
        // 1. Supprime l'ancienne table si elle existe (attention : les données locales seront perdues)
        String requeteSuppressionTable = String.format("drop table if exists %s ",
                QuizContract.TABLE_NAME);
        db.execSQL(requeteSuppressionTable);
        // 2. Recrée la table avec la nouvelle structure définie dans onCreate
        onCreate(db);
    }
}
