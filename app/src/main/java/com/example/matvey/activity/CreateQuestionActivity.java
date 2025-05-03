package com.example.matvey.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.matvey.R;
import com.example.matvey.adapter.TestSpinnerAdapter;
import com.example.matvey.data.Test;
import com.example.matvey.data.TestQuestion;
import com.example.matvey.database.DbManager;

import java.util.List;

public class CreateQuestionActivity extends AppCompatActivity {
    private DbManager dbManager;
    private List<Test> tests;
    private long selectedTestId = -1;

    private EditText questionTextEditText;
    private EditText option1EditText, option2EditText, option3EditText;
    private RadioGroup correctAnswerRadioGroup;
    private Spinner testSpinner;
    private Button createQuestionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        dbManager = new DbManager(this);
        dbManager.openDb();

        questionTextEditText = findViewById(R.id.question_text_edit_text);
        option1EditText = findViewById(R.id.option1_edit_text);
        option2EditText = findViewById(R.id.option2_edit_text);
        option3EditText = findViewById(R.id.option3_edit_text);
        correctAnswerRadioGroup = findViewById(R.id.correct_answer_radio_group);
        testSpinner = findViewById(R.id.test_spinner);
        createQuestionButton = findViewById(R.id.create_question_button);

        // Загрузка тестов для выпадающего списка
        new LoadTestsTask().execute();

        createQuestionButton.setOnClickListener(v -> {
            if (selectedTestId == -1) {
                Toast.makeText(this, "Выберите тест", Toast.LENGTH_SHORT).show();
                return;
            }

            String questionText = questionTextEditText.getText().toString().trim();
            String option1 = option1EditText.getText().toString().trim();
            String option2 = option2EditText.getText().toString().trim();
            String option3 = option3EditText.getText().toString().trim();

            if (questionText.isEmpty() || option1.isEmpty() ||
                    option2.isEmpty() || option3.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedId = correctAnswerRadioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Выберите правильный ответ", Toast.LENGTH_SHORT).show();
                return;
            }

            int correctAnswer = correctAnswerRadioGroup.indexOfChild(
                    findViewById(selectedId)) + 1;

            TestQuestion question = new TestQuestion();
            question.setQuestionText(questionText);
            question.setOption1(option1);
            question.setOption2(option2);
            question.setOption3(option3);
            question.setCorrectAnswer(correctAnswer);
            question.setTestId(selectedTestId);

            new CreateQuestionTask().execute(question);
        });
    }

    private class LoadTestsTask extends AsyncTask<Void, Void, List<Test>> {
        @Override
        protected List<Test> doInBackground(Void... voids) {
            return dbManager.getAllTests();
        }

        @Override
        protected void onPostExecute(List<Test> result) {
            tests = result;
            if (tests != null && !tests.isEmpty()) {
                // Настройка адаптера для Spinner
                TestSpinnerAdapter adapter = new TestSpinnerAdapter(
                        CreateQuestionActivity.this, tests);
                testSpinner.setAdapter(adapter);

                testSpinner.setOnItemSelectedListener(
                        new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                selectedTestId = tests.get(position).getId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                selectedTestId = -1;
                            }
                        });
            } else {
                Toast.makeText(CreateQuestionActivity.this,
                        "Нет доступных тестов", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class CreateQuestionTask extends AsyncTask<TestQuestion, Void, Long> {
        @Override
        protected Long doInBackground(TestQuestion... params) {
            return dbManager.addQuestion(params[0]);
        }

        @Override
        protected void onPostExecute(Long questionId) {
            if (questionId != -1) {
                Toast.makeText(CreateQuestionActivity.this,
                        "Вопрос добавлен!", Toast.LENGTH_SHORT).show();
                clearForm();
            } else {
                Toast.makeText(CreateQuestionActivity.this,
                        "Ошибка при добавлении вопроса", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clearForm() {
        questionTextEditText.setText("");
        option1EditText.setText("");
        option2EditText.setText("");
        option3EditText.setText("");
        correctAnswerRadioGroup.clearCheck();
    }

    @Override
    protected void onDestroy() {
        dbManager.closeDb();
        super.onDestroy();
    }
}