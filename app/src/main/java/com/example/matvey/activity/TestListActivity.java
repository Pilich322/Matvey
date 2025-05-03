package com.example.matvey.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matvey.R;
import com.example.matvey.adapter.TestAdapter;
import com.example.matvey.data.Test;
import com.example.matvey.database.DbManager;

import java.util.List;

public class TestListActivity extends AppCompatActivity {
    private DbManager dbManager;
    private RecyclerView testsRecyclerView;
    private TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        dbManager = new DbManager(this);
        dbManager.openDb();
        testsRecyclerView = findViewById(R.id.tests_recycler_view);
        testsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new LoadTestsTask().execute();
    }

    private class LoadTestsTask extends AsyncTask<Void, Void, List<Test>> {
        @Override
        protected List<Test> doInBackground(Void... voids) {
            return dbManager.getAllTests();
        }

        @Override
        protected void onPostExecute(List<Test> tests) {
            adapter = new TestAdapter(tests, test -> {
                Intent intent = new Intent(TestListActivity.this, TestActivity.class);
                intent.putExtra("test_id", test.getId());
                startActivity(intent);
            });
            testsRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onDestroy() {
        dbManager.closeDb();
        super.onDestroy();
    }
}