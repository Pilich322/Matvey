package com.example.matvey.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.matvey.R;
import com.example.matvey.database.DbHelper;
import com.example.matvey.database.DbManager;

public class CreateTestActivity extends AppCompatActivity {
    private DbManager dbManager;
    private EditText testNameEditText;
    private Button createTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);

        dbManager = new DbManager(this);
        dbManager.openDb();
        testNameEditText = findViewById(R.id.test_name_edit_text);
        createTestButton = findViewById(R.id.create_test_button);

        createTestButton.setOnClickListener(v -> {
            String testName = testNameEditText.getText().toString().trim();
            if (testName.isEmpty()) {
                Toast.makeText(this, "Введите название теста", Toast.LENGTH_SHORT).show();
                return;
            }

            new CreateTestTask().execute(testName);
        });
    }

    private class CreateTestTask extends AsyncTask<String, Void, Long> {
        @Override
        protected Long doInBackground(String... params) {
            return dbManager.addTest(params[0]);
        }

        @Override
        protected void onPostExecute(Long testId) {
            if (testId != -1) {
                Toast.makeText(CreateTestActivity.this,
                        "Тест создан! ID: " + testId, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(CreateTestActivity.this,
                        "Ошибка при создании теста", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        dbManager.closeDb();
        super.onDestroy();
    }
}