package com.example.webviewtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.Gesture;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ArrayList<Food> mArrayList;
    private CustomAdapter mAdapter;
    private Button btn_save;
//    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_main_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // 저장된값 불러오기
        loadData();
//        mArrayList = new ArrayList<>();
//        mAdapter = new CustomAdapter(mArrayList);
        mAdapter = new CustomAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

//        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Food food = mArrayList.get(position);
//                Toast.makeText(getApplicationContext(), food.getName(), Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(getBaseContext(), ResultActivity.class);
//                intent.putExtra("name", food.getName());
//                intent.putExtra("nowDate", food.getNowDate());
//                intent.putExtra("date", food.getDate());
//                intent.putExtra("count", food.getCount());
//
//                startActivity(intent);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
//
//        Button buttonInsert = (Button)findViewById(R.id.btn_main_insert);
//        buttonInsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ListActivity.this, AddActivity.class);
//                startActivity(intent);
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
//                View view = LayoutInflater.from(ListActivity.this).inflate(R.layout.activity_add, null, false);
//                builder.setView(view);
//
//                final Button ButtonSubmit = (Button)view.findViewById(R.id.btn_finish);
//                final EditText etName = (EditText)view.findViewById(R.id.et_name);
//                final EditText etNowDate = (EditText)view.findViewById(R.id.et_nowDate);
//                final EditText etDate = (EditText)view.findViewById(R.id.et_date);
//                final EditText etCount = (EditText)view.findViewById(R.id.et_count);
//                ButtonSubmit.setText("추가");
//
//                final AlertDialog dialog = builder.create();
//
//                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String strName = etName.getText().toString();
//                        String strNowDate = etNowDate.getText().toString();
//                        String strDate = etDate.getText().toString();
//                        String strCount = etCount.getText().toString();
//
//                        Food food = new Food(strName, strNowDate, strDate, strCount);
//                        mArrayList.add(food);
//                        mAdapter.notifyItemInserted(0);
//
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
//                count++;
//
//                Food data = new Food(count+"Apple","2020년 04월 14일", "2020년 04월 19일", count+"개");
//                mArrayList.add(data);
//                mAdapter.notifyDataSetChanged();
//            }
//        });

        // AddActivity에서 넘어오는 값 Intent로 받기
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String getName = extras.getString("addName");
            String getNowDate = extras.getString("addNowDate");
            String getDate = extras.getString("addDate");
            String getCount = extras.getString("addCount");

            Food getFood = new Food(getName, getNowDate, getDate, getCount);
            mArrayList.add(getFood);
            mAdapter.notifyItemInserted(0);
        }

        // 현재 ListActivity에 있는 값들을 저장 버튼을 누를 시 저장
        btn_save = findViewById(R.id.btn_main_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveDate();
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

//    // 데이터저장하는 함수
//    public void saveDate() {
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(mArrayList);
//        editor.putString("task list", json);
//        editor.apply();
//    }

    // 저장된 값들 불러오는 함
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
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
//        saveDate();
    }

    //    public interface ClickListener {
//        void onClick(View view, int position);
//        void onLongClick(View view, int position);
//    }
//
//    private class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
//        private GestureDetector gestureDetector;
//        private ListActivity.ClickListener clickListener;
//
//        public RecyclerTouchListener(Context context,final RecyclerView recyclerView,final ListActivity.ClickListener clickListener) {
//            this.clickListener = clickListener;
//            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//                    return true;
//                }
//
//                @Override
//                public void onLongPress(MotionEvent e) {
//                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
//                    if (child != null && clickListener != null) {
//                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
//                    }
//                }
//            });
//        }
//
//        @Override
//        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//            View child = rv.findChildViewUnder(e.getX(), e.getY());
//            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
//                clickListener.onClick(child, rv.getChildAdapterPosition(child));
//            }
//            return false;
//        }
//
//        @Override
//        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//        }
//
//        @Override
//        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//        }
//    }
}
