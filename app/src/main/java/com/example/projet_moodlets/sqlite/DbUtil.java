package com.example.projet_moodlets.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbUtil extends SQLiteOpenHelper {
    private static final String DB_NAME = "MINI_MOODLE.DB";
    private static final int DB_VERSION = 1;
    public DbUtil(Context context) {
        // Probleme si je fait comme cela aulieux de prendre la version par le contract de la table?
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QuizResultContract.CREATE_TABLE);
        db.execSQL(TravailSoumissionContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
