package com.example.webviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RefriActivity extends AppCompatActivity {

    private ArrayList<Food> mArrayList;
    private RefriAdapter mAdapter;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refri);

        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_refri_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        loadData();

        mAdapter = new RefriAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String getName = extras.getString("addName_refri");
            String getNowDate = extras.getString("addNowDate_refri");
            String getDate = extras.getString("addDate_refri");
            String getCount = extras.getString("addCount_refri");

            Food getFood = new Food(getName, getNowDate, getDate, getCount);
            mArrayList.add(getFood);
            mAdapter.notifyItemInserted(0);
        }

        btn_save = findViewById(R.id.btn_main_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRefriDate();
                Intent intent = new Intent(RefriActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    // 데이터저장하는 함수
    public void saveRefriDate() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences refri", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mArrayList);
        editor.putString("task list_refri", json);
        editor.apply();
    }

    // 저장된 값들 불러오는 함
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences refri", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list_refri", null);
        Type type = new TypeToken<ArrayList<Food>>() {}.getType();
        mArrayList = gson.fromJson(json, type);

        if (mArrayList == null) {
            mArrayList = new ArrayList<>();
        }
    }

    // 뒤로가기를 눌렀을때도 데이터 저장
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveRefriDate();
    }
}
