package com.example.matvey.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.matvey.data.Test;
import com.example.matvey.data.TestQuestion;
import com.example.matvey.data.TestResult;

import java.util.ArrayList;
import java.util.List;

public class DbManager {

    private DbHelper dbHelper;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase db;
    public DbManager(Context context){
        dbHelper = new DbHelper(context);
    }
    public void openDb(){
        db = dbHelper.getWritableDatabase();
    }
    public void closeDb(){
        dbHelper.close();
    }
    public long addTest(String testName) {
        ContentValues values = new ContentValues();
        values.put(DbConst.TEST_NAME, testName);
        return db.insert(DbConst.TESTS_TABLE_NAME, null, values);
    }

    public List<Test> getAllTests() {
        List<Test> tests = new ArrayList<>();

        Cursor cursor = db.query(DbConst.TESTS_TABLE_NAME,
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Test test = new Test();
                test.setId(cursor.getInt(0));
                test.setName(cursor.getString(1));
                tests.add(test);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tests;
    }

    public long addQuestion(TestQuestion question) {
        ContentValues values = new ContentValues();

        values.put(DbConst.QUESTION_TEXT, question.getQuestionText());
        values.put(DbConst.QUESTION_OPTION_1, question.getOption1());
        values.put(DbConst.QUESTION_OPTION_2, question.getOption2());
        values.put(DbConst.QUESTION_OPTION_3, question.getOption3());
        values.put(DbConst.QUESTION_CORRECT_ANSWER, question.getCorrectAnswer());
        values.put(DbConst.QUESTION_TEST_FK, question.getTestId());

        return db.insert(DbConst.QUESTIONS_TABLE_NAME, null, values);
    }

    public void addResult(String userName, int score, long testId) {
        ContentValues values = new ContentValues();

        values.put(DbConst.RESULT_USER_NAME, userName);
        values.put(DbConst.RESULT_SCORE, score);
        values.put(DbConst.RESULT_DATE, System.currentTimeMillis());
        values.put(DbConst.RESULT_TEST_FK, testId);

        db.insert(DbConst.RESULTS_TABLE_NAME, null, values);
    }

    public List<TestQuestion> getAllQuestions() {
        List<TestQuestion> questions = new ArrayList<>();

        Cursor cursor = db.query(DbConst.QUESTIONS_TABLE_NAME,
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                TestQuestion question = new TestQuestion();
                question.setId(cursor.getInt(0));
                question.setQuestionText(cursor.getString(1));
                question.setOption1(cursor.getString(2));
                question.setOption2(cursor.getString(3));
                question.setOption3(cursor.getString(4));
                question.setCorrectAnswer(cursor.getInt(5));
                question.setTestId(cursor.getLong(6));
                questions.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questions;
    }

    public List<TestResult> getAllResults() {
        List<TestResult> results = new ArrayList<>();

        Cursor cursor = db.query(DbConst.RESULTS_TABLE_NAME,
                null, null, null, null, null, DbConst.RESULT_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                TestResult result = new TestResult();
                result.setId(cursor.getInt(0));
                result.setUserName(cursor.getString(1));
                result.setScore(cursor.getInt(2));
                result.setDate(cursor.getString(3));
                result.setTestId(cursor.getLong(4));
                results.add(result);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return results;
    }

    public List<TestQuestion> getQuestionsForTest(long testId) {
        List<TestQuestion> questions = new ArrayList<>();

        Cursor cursor = db.query(DbConst.QUESTIONS_TABLE_NAME,
                null, DbConst.QUESTION_TEST_FK + "=?",
                new String[]{String.valueOf(testId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                TestQuestion question = new TestQuestion();
                question.setId(cursor.getInt(0));
                question.setQuestionText(cursor.getString(1));
                question.setOption1(cursor.getString(2));
                question.setOption2(cursor.getString(3));
                question.setOption3(cursor.getString(4));
                question.setCorrectAnswer(cursor.getInt(5));
                question.setTestId(cursor.getLong(6));
                questions.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questions;
    }

    public List<TestResult> getResultsForTest(long testId) {
        List<TestResult> results = new ArrayList<>();

        Cursor cursor = db.query(DbConst.RESULTS_TABLE_NAME,
                null,
                DbConst.RESULT_TEST_FK + "=?",
                new String[]{String.valueOf(testId)},
                null, null,
                DbConst.RESULT_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                TestResult result = new TestResult();
                result.setId(cursor.getLong(0));
                result.setUserName(cursor.getString(1));
                result.setScore(cursor.getInt(2));
                result.setDate(cursor.getString(3));
                result.setTestId(cursor.getLong(4));
                results.add(result);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return results;
    }
}