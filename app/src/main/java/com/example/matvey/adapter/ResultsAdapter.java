package com.example.matvey.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matvey.R;
import com.example.matvey.data.Test;
import com.example.matvey.data.TestResult;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultViewHolder> {
    private List<TestResult> results;
    private boolean showTestName;
    private List<Test> tests; // Для отображения названий тестов

    public ResultsAdapter(List<TestResult> results, boolean showAllResults) {
        this.results = results;
        this.showTestName = showAllResults;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        TestResult result = results.get(position);
        holder.userNameText.setText(result.getUserName());
        holder.scoreText.setText(result.getScore() + " баллов");
        holder.dateText.setText(result.getDate());

        if (showTestName && tests != null) {
            for (Test test : tests) {
                if (test.getId() == result.getTestId()) {
                    holder.testNameText.setVisibility(View.VISIBLE);
                    holder.testNameText.setText("Тест: " + test.getName());
                    break;
                }
            }
        } else {
            holder.testNameText.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
        notifyDataSetChanged();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView userNameText, scoreText, dateText, testNameText;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameText = itemView.findViewById(R.id.user_name);
            scoreText = itemView.findViewById(R.id.score);
            dateText = itemView.findViewById(R.id.date);
            testNameText = itemView.findViewById(R.id.test_name);
        }
    }
}