package com.example.matvey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.matvey.data.Test;

import java.util.List;

public class TestSpinnerAdapter extends ArrayAdapter<Test> {
    private Context context;
    private List<Test> tests;

    public TestSpinnerAdapter(Context context, List<Test> tests) {
        super(context, android.R.layout.simple_spinner_item, tests);
        this.context = context;
        this.tests = tests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(tests.get(position).getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(tests.get(position).getName());

        return convertView;
    }
}
