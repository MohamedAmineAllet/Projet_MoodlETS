package com.example.projet_moodlets.modele.daos.Quiz.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DbUtil extends SQLiteOpenHelper {


    public DbUtil(Context context) {
        super(context, QuizContract.DB_NAME, null, QuizContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String requeteCreation = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s REAL, %s REAL, %s TEXT)",
                QuizContract.TABLE_NAME,
                QuizContract.Colonnes.ID,
                QuizContract.Colonnes.TITLE,
                QuizContract.Colonnes.STATUS,
                QuizContract.Colonnes.GRADE,
                QuizContract.Colonnes.TOTAL_POINTS,
                QuizContract.Colonnes.SUBMISSION_DATE
        );
        db.execSQL(requeteCreation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("DbUtil::onUpgrade","modification du schema");
        String requeteSuppressionTable = String.format("drop table if exists %s ",
                QuizContract.TABLE_NAME);
        db.execSQL(requeteSuppressionTable);
        onCreate(db);
    }
}
