package com.example.matvey.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, DbConst.DATABASE_NAME, null, DbConst.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbConst.CREATE_TESTS_TABLE);
        db.execSQL(DbConst.CREATE_QUESTIONS_TABLE);
        db.execSQL(DbConst.CREATE_RESULTS_TABLE);
        addStartData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbConst.DROP_TEST_TABLE);
        db.execSQL(DbConst.DROP_QUESTIONS_TABLE);
        db.execSQL(DbConst.DROP_RESULTS_TABLE);
        onCreate(db);
    }

    private void addStartData(SQLiteDatabase db) {
        ContentValues testValues = new ContentValues();
        testValues.put(DbConst.TEST_NAME, "Общий тест");
        long testId = db.insert(DbConst.TESTS_TABLE_NAME, null, testValues);

        // Добавляем вопросы для теста
        ContentValues values = new ContentValues();

        // Вопрос 1
        values.put(DbConst.QUESTION_TEXT, "Сколько планет в Солнечной системе?");
        values.put(DbConst.QUESTION_OPTION_1, "7");
        values.put(DbConst.QUESTION_OPTION_3, "8");
        values.put(DbConst.QUESTION_OPTION_2, "9");
        values.put(DbConst.QUESTION_CORRECT_ANSWER, 2);
        values.put(DbConst.QUESTION_TEST_FK, testId);
        db.insert(DbConst.QUESTIONS_TABLE_NAME, null, values);
        values.clear();

        // Вопрос 2
        values.put(DbConst.QUESTION_TEXT, "Столица Франции?");
        values.put(DbConst.QUESTION_OPTION_1, "Лондон");
        values.put(DbConst.QUESTION_OPTION_2, "Париж");
        values.put(DbConst.QUESTION_OPTION_3, "Берлин");
        values.put(DbConst.QUESTION_CORRECT_ANSWER, 2);
        values.put(DbConst.QUESTION_TEST_FK, testId);
        db.insert(DbConst.QUESTIONS_TABLE_NAME, null, values);
    }
}
