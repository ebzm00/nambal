package com.example.webviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String name = "";
        String nowDate = "";
        String date = "";
        String count = "";

        Bundle extras = getIntent().getExtras();

        name = extras.getString("name");
        nowDate = extras.getString("nowDate");
        date = extras.getString("date");
        count = extras.getString("count");

        TextView textView = (TextView)findViewById(R.id.textView_result);

        String str = name + '\n' + nowDate + '\n' + date + '\n' + count;
        textView.setText(str);
    }
}
