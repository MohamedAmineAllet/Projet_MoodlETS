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

public class QuizLocalDao implements QuizDao{
    private Context context;
    private List<Quiz> quiz = new ArrayList<>();

    public QuizLocalDao(Context context) {
        this.context = context;
    }

    @Override
    public List<String> getTitresDesQuiz() {
        List<String> lesQuiz = new ArrayList<>();
        for(Quiz q: quiz){
            lesQuiz.add(q.getTitle());
        }
        return lesQuiz;
    }

    @Override
    public List<Quiz> getQuiz() {
        return new ArrayList<>();
    }

    @Override
    public Quiz getQuizParId(int id) {
        for(Quiz q:quiz){
            if(q.getId() == id){
                return q;
            }
        }return null;
    }

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

        db.insert(QuizContract.TABLE_NAME, null, values);
        db.close();
    }

    public void sauvegarderResultatQuiz(Quiz quiz){
        DbUtil dbUtil = new DbUtil(context);
        SQLiteDatabase db = dbUtil.getReadableDatabase();


        Cursor cursor = db.query(QuizContract.TABLE_NAME,
                null,
                QuizContract.Colonnes.ID + "=?",
                new String[]{String.valueOf(quiz.getId())},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            quiz.setGrade(cursor.getDouble(cursor.getColumnIndexOrThrow(QuizContract.Colonnes.GRADE)));
            quiz.setTotalPoints(cursor.getDouble(cursor.getColumnIndexOrThrow(QuizContract.Colonnes.TOTAL_POINTS)));
            quiz.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(QuizContract.Colonnes.STATUS)));
            quiz.setSubmissionDate(cursor.getString(cursor.getColumnIndexOrThrow(QuizContract.Colonnes.SUBMISSION_DATE)));
            cursor.close();
        }
        db.close();
    }
}
