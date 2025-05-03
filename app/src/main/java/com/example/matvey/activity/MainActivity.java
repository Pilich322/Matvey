package com.example.matvey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.matvey.R;
import com.example.matvey.database.DbManager;

public class MainActivity extends AppCompatActivity {
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DbManager(this);

        Button startTestBtn = findViewById(R.id.start_test_btn);
        Button viewResultsBtn = findViewById(R.id.view_results_btn);
        Button createTestBtn = findViewById(R.id.create_test_btn);
        Button createQuestionBtn = findViewById(R.id.create_question_btn);

        startTestBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TestListActivity.class);
            startActivity(intent);
        });

        viewResultsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            startActivity(intent);
        });

        createTestBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateTestActivity.class);
            startActivity(intent);
        });

        createQuestionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateQuestionActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        dbManager.closeDb();
        super.onDestroy();
    }
}