package com.example.matvey.database;

public class DbConst {
    public static final String DATABASE_NAME = "TestApp.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TESTS_TABLE_NAME = "tests";
    public static final String TEST_ID = "id";
    public static final String TEST_NAME = "name";

    public static final String QUESTIONS_TABLE_NAME = "questions";
    public static final String QUESTION_ID = "id";
    public static final String QUESTION_TEXT = "question_text";
    public static final String QUESTION_OPTION_1 = "option1";
    public static final String QUESTION_OPTION_2 = "option2";
    public static final String QUESTION_OPTION_3 = "option3";
    public static final String QUESTION_CORRECT_ANSWER = "correct_answer";
    public static final String QUESTION_TEST_FK = "test_id";

    public static final String RESULTS_TABLE_NAME = "results";
    public static final String RESULT_ID = "id";
    public static final String RESULT_USER_NAME = "user_name";
    public static final String RESULT_SCORE = "score";
    public static final String RESULT_DATE = "date";
    public static final String RESULT_TEST_FK = "test_id";

    public static final String CREATE_TESTS_TABLE = "CREATE TABLE " + TESTS_TABLE_NAME + "("
            + TEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TEST_NAME + " TEXT)";

    public static final String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + QUESTIONS_TABLE_NAME + "("
            + QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + QUESTION_TEXT + " TEXT,"
            + QUESTION_OPTION_1 + " TEXT,"
            + QUESTION_OPTION_2 + " TEXT,"
            + QUESTION_OPTION_3 + " TEXT,"
            + QUESTION_CORRECT_ANSWER + " INTEGER,"
            + QUESTION_TEST_FK + " INTEGER,"
            + "FOREIGN KEY(" + QUESTION_TEST_FK + ") REFERENCES "
            + TESTS_TABLE_NAME + "(" + TEST_ID + "))";

    public static final String CREATE_RESULTS_TABLE = "CREATE TABLE " + RESULTS_TABLE_NAME + "("
            + RESULT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + RESULT_USER_NAME + " TEXT,"
            + RESULT_SCORE + " INTEGER,"
            + RESULT_DATE + " TEXT,"
            + RESULT_TEST_FK + " INTEGER,"
            + "FOREIGN KEY(" + RESULT_TEST_FK + ") REFERENCES "
            + TESTS_TABLE_NAME + "(" + TEST_ID + "))";

    public static final String DROP_TEST_TABLE = "DROP TABLE IF EXISTS " + TESTS_TABLE_NAME;
    public static final String DROP_QUESTIONS_TABLE = "DROP TABLE IF EXISTS " + QUESTIONS_TABLE_NAME;
    public static final String DROP_RESULTS_TABLE = "DROP TABLE IF EXISTS " + RESULTS_TABLE_NAME;
}
