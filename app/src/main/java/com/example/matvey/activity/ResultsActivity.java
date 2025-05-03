package com.example.matvey.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matvey.R;
import com.example.matvey.adapter.ResultsAdapter;
import com.example.matvey.data.TestResult;
import com.example.matvey.database.DbManager;

import java.util.List;

public class ResultsActivity extends AppCompatActivity {
    private DbManager dbManager;
    private RecyclerView resultsRecyclerView;
    private ResultsAdapter adapter;
    private boolean showAllResults = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        dbManager = new DbManager(this);
        dbManager.openDb();
        resultsRecyclerView = findViewById(R.id.results_recycler_view);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        long testId = getIntent().getLongExtra("test_id", -1);
        if (testId != -1) {
            showAllResults = false;
            new LoadResultsForTestTask().execute(testId);
        } else {
            new LoadAllResultsTask().execute();
        }
    }

    private class LoadAllResultsTask extends AsyncTask<Void, Void, List<TestResult>> {
        @Override
        protected List<TestResult> doInBackground(Void... voids) {
            return dbManager.getAllResults();
        }

        @Override
        protected void onPostExecute(List<TestResult> results) {
            if (results != null && !results.isEmpty()) {
                adapter = new ResultsAdapter(results, showAllResults);
                resultsRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(ResultsActivity.this,
                        "Нет результатов для отображения", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private class LoadResultsForTestTask extends AsyncTask<Long, Void, List<TestResult>> {
        @Override
        protected List<TestResult> doInBackground(Long... testIds) {
            return dbManager.getResultsForTest(testIds[0]);
        }

        @Override
        protected void onPostExecute(List<TestResult> results) {
            if (results != null && !results.isEmpty()) {
                adapter = new ResultsAdapter(results, showAllResults);
                resultsRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(ResultsActivity.this,
                        "Нет результатов для этого теста", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        dbManager.closeDb();
        super.onDestroy();
    }
}