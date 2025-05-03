package com.example.matvey.activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.matvey.R;
import com.example.matvey.data.TestQuestion;
import com.example.matvey.database.DbManager;

import java.util.List;

public class TestActivity extends AppCompatActivity {
    private DbManager  dbManager;
    private List<TestQuestion> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private TextView questionText;
    private RadioGroup optionsGroup;
    private RadioButton option1, option2, option3;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        dbManager = new DbManager(this);
        dbManager.openDb();

        questionText = findViewById(R.id.question_text);
        optionsGroup = findViewById(R.id.options_group);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        nextButton = findViewById(R.id.next_button);

        // Загрузка вопросов в фоне
        new LoadQuestionsTask().execute();

        nextButton.setOnClickListener(v -> {
            int selectedId = optionsGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Пожалуйста, выберите ответ", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadioButton = findViewById(selectedId);
            int answerIndex = optionsGroup.indexOfChild(selectedRadioButton) + 1;

            if (answerIndex == questions.get(currentQuestionIndex).getCorrectAnswer()) {
                score++;
            }

            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                showQuestion(currentQuestionIndex);
            } else {
                // Тест завершен
                finishTest();
            }
        });
    }

    private void showQuestion(int index) {
        TestQuestion question = questions.get(index);
        questionText.setText(question.getQuestionText());
        option1.setText(question.getOption1());
        option2.setText(question.getOption2());
        option3.setText(question.getOption3());
        optionsGroup.clearCheck();

        if (index == questions.size() - 1) {
            nextButton.setText("Завершить");
        }
    }

    private void finishTest() {
        long testId = getIntent().getLongExtra("test_id", -1);
        if (testId == -1) {
            Toast.makeText(this, "Ошибка: не указан тест", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String userName = "User"; // В реальном приложении запрашиваем имя
        new SaveResultTask().execute(userName, String.valueOf(score), String.valueOf(testId));

        Toast.makeText(this, "Тест завершен! Ваш результат: " + score + "/" + questions.size(),
                Toast.LENGTH_LONG).show();
        finish();
    }

    private class LoadQuestionsTask extends AsyncTask<Void, Void, List<TestQuestion>> {
        @Override
        protected List<TestQuestion> doInBackground(Void... voids) {
            long testId = getIntent().getLongExtra("test_id", -1);
            if (testId == -1) {
                return null;
            }
            return dbManager.getQuestionsForTest(testId);
        }

        @Override
        protected void onPostExecute(List<TestQuestion> result) {
            questions = result;
            if (questions != null && !questions.isEmpty()) {
                showQuestion(0);
            } else {
                Toast.makeText(TestActivity.this, "Вопросы не найдены", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private class SaveResultTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String userName = params[0];
            int score = Integer.parseInt(params[1]);
            long testId = Long.parseLong(params[2]);
            dbManager.addResult(userName, score, testId);
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        dbManager.closeDb();
        super.onDestroy();
    }
}