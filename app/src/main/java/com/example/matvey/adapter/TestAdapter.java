package com.example.matvey.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matvey.R;
import com.example.matvey.data.Test;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
    private List<Test> tests;
    private OnTestClickListener listener;

    public interface OnTestClickListener {
        void onTestClick(Test test);
    }

    public TestAdapter(List<Test> tests, OnTestClickListener listener) {
        this.tests = tests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_test, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        Test test = tests.get(position);
        holder.testNameText.setText(test.getName());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTestClick(test);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    static class TestViewHolder extends RecyclerView.ViewHolder {
        TextView testNameText;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            testNameText = itemView.findViewById(R.id.test_name);
        }
    }
}