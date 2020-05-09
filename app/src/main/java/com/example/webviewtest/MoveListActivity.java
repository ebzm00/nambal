package com.example.webviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MoveListActivity extends AppCompatActivity {

    private Button btn_freeze, btn_refri, btn_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_list);

        btn_freeze = findViewById(R.id.btn_freeze);
        btn_freeze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveFreeze = new Intent(MoveListActivity.this, FreezeActivity.class);
                startActivity(moveFreeze);
                finish();
            }
        });

        btn_refri = findViewById(R.id.btn_refri);
        btn_refri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveRefri = new Intent(MoveListActivity.this, RefriActivity.class);
                startActivity(moveRefri);
                finish();
            }
        });

        btn_out = findViewById(R.id.btn_out);
        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveOut = new Intent(MoveListActivity.this, OutActivity.class);
                startActivity(moveOut);
                finish();
            }
        });
    }
}
