package com.example.projet_moodlets.modele.daos.Quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projet_moodlets.modele.daos.Quiz.sql.DbUtil;
import com.example.projet_moodlets.modele.daos.Quiz.sql.QuizContract;
import com.example.projet_moodlets.modele.entites.Quiz;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du DAO pour le stockage local SQLite des quiz.
 */
public class QuizLocalDao implements QuizDao {
    private Context context;
    private List<Quiz> quiz = new ArrayList<>();


    public QuizLocalDao(Context context) {
        this.context = context;
    }


    /**
     * Récupère la liste des quiz stockés localement.
     *
     * @return Une liste de quiz (actuellement vide par défaut).
     */
    @Override
    public List<Quiz> getQuiz() {
        return new ArrayList<>();
    }

    /**
     * Recherche un quiz spécifique dans la liste locale par son ID.
     *
     * @param id Identifiant du quiz recherché.
     * @return L'objet Quiz correspondant ou null.
     */
    @Override
    public Quiz getQuizParId(int id) {
        for (Quiz q : quiz) {
            if (q.getId() == id) {
                return q;
            }
        }
        return null;
    }

    /**
     * Met à jour les informations d'un quiz dans la base de données SQLite.
     *
     * @param quiz L'objet contenant les nouvelles données à enregistrer.
     */
    @Override
    public void modifier(Quiz quiz) throws IOException, JSONException {
        DbUtil dbUtil = new DbUtil(context);
        SQLiteDatabase db = dbUtil.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(QuizContract.Colonnes.TITLE, quiz.getTitle());
        values.put(QuizContract.Colonnes.STATUS, quiz.getStatus());
        values.put(QuizContract.Colonnes.GRADE, quiz.getGrade());
        values.put(QuizContract.Colonnes.TOTAL_POINTS, quiz.getTotalPoints());
        values.put(QuizContract.Colonnes.SUBMISSION_DATE, quiz.getSubmissionDate());

        db.update(QuizContract.TABLE_NAME, values, QuizContract.Colonnes.ID + " = ?", new String[]{String.valueOf(quiz.getId())});
        db.close();
    }

    /**
     * Charge les données persistantes (note, statut) depuis SQLite vers l'objet Quiz fourni.
     *
     * @param quiz L'objet Quiz à synchroniser avec la base locale.
     */
    public void sauvegarderResultatQuiz(Quiz quiz) {
        DbUtil dbUtil = new DbUtil(context);
        SQLiteDatabase db = dbUtil.getReadableDatabase();


        Cursor cursor = db.query(QuizContract.TABLE_NAME,
                null,
                QuizContract.Colonnes.ID + "=?",
                new String[]{String.valueOf(quiz.getId())},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                quiz.setGrade(cursor.getDouble(cursor.getColumnIndexOrThrow(QuizContract.Colonnes.GRADE)));
                quiz.setTotalPoints(cursor.getDouble(cursor.getColumnIndexOrThrow(QuizContract.Colonnes.TOTAL_POINTS)));
                quiz.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(QuizContract.Colonnes.STATUS)));
                quiz.setSubmissionDate(cursor.getString(cursor.getColumnIndexOrThrow(QuizContract.Colonnes.SUBMISSION_DATE)));
            }
            cursor.close();
        }
        db.close();
    }
}
