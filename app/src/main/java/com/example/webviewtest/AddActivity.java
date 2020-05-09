package com.example.webviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Inet4Address;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button btn_carema, btn_nowDate, btn_date, btn_finish;
    private EditText et_name, et_nowDate, et_date, et_count;
    private Spinner spinner;
    private RadioButton rb_freeze, rb_refri, rb_out;
    private RadioGroup radioGroup;
    private int checked = 0;

    private ArrayList<Food> arrayList;
//    private CustomAdapter adapter;
    private FreezeAdapter fAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btn_carema = findViewById(R.id.btn_camera);
        btn_nowDate = findViewById(R.id.btn_nowDate);
        btn_date = findViewById(R.id.btn_date);
        btn_finish = findViewById(R.id.btn_finish);
        et_name = findViewById(R.id.et_name);
        et_nowDate = findViewById(R.id.et_nowDate);
        et_date = findViewById(R.id.et_date);
        et_count = findViewById(R.id.et_count);
        spinner = findViewById(R.id.spinner);
        rb_freeze = findViewById(R.id.rb_freeze);
        rb_refri = findViewById(R.id.rb_refri);
        rb_out = findViewById(R.id.rb_out);
        radioGroup = findViewById(R.id.radioGroup);

        arrayList = new ArrayList<>();
//        adapter = new CustomAdapter(this, arrayList);
        fAdapter = new FreezeAdapter(this, arrayList);

        Button buttonInsert = (Button)findViewById(R.id.btn_finish);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String setName = et_name.getText().toString();
                String setNowDate = et_nowDate.getText().toString();
                String setDate = et_date.getText().toString();
                String setCount = et_count.getText().toString();
                Food food = new Food(setName,setNowDate,setDate,setCount);

                if (rb_freeze.isChecked()) {
                    checked = 1;
                } else if (rb_refri.isChecked()) {
                    checked = 2;
                } else if (rb_out.isChecked()) {
                    checked = 3;
                }

                switch (checked) {
                    case 1:
                        Intent intentFreeze = new Intent(getBaseContext(), FreezeActivity.class);
                        intentFreeze.putExtra("addName_freeze", food.getName());
                        intentFreeze.putExtra("addNowDate_freeze", food.getNowDate());
                        intentFreeze.putExtra("addDate_freeze", food.getDate());
                        intentFreeze.putExtra("addCount_freeze", food.getCount());
                        startActivity(intentFreeze);
                        finish();
                        break;

                    case 2:
                        Intent intentRefri = new Intent(getBaseContext(), RefriActivity.class);
                        intentRefri.putExtra("addName_refri", food.getName());
                        intentRefri.putExtra("addNowDate_refri", food.getNowDate());
                        intentRefri.putExtra("addDate_refri", food.getDate());
                        intentRefri.putExtra("addCount_refri", food.getCount());
                        startActivity(intentRefri);
                        finish();
                        break;

                    case 3:
                        Intent intentOut = new Intent(getBaseContext(), OutActivity.class);
                        intentOut.putExtra("addName_out", food.getName());
                        intentOut.putExtra("addNowDate_out", food.getNowDate());
                        intentOut.putExtra("addDate_out", food.getDate());
                        intentOut.putExtra("addCount_out", food.getCount());
                        startActivity(intentOut);
                        finish();
                        break;
                }
                if(setName.getBytes().length<=0){
                    Toast.makeText(AddActivity.this, "식재료를 입력하세요." ,Toast.LENGTH_SHORT).show();
                }else if(setNowDate.getBytes().length<=0){
                    Toast.makeText(AddActivity.this, "날짜를 입력하세요." ,Toast.LENGTH_SHORT).show();
                }else if(setDate.getBytes().length<=0){
                    Toast.makeText(AddActivity.this, "유통기한을 입력하세요." ,Toast.LENGTH_SHORT).show();
                }else if(setCount.getBytes().length<=0){
                    Toast.makeText(AddActivity.this, "수량를 입력하세요." ,Toast.LENGTH_SHORT).show();
                }
            }
        });

        // et_name에 가져온 값 입력
        Intent getintent = getIntent();
        String name = getintent.getStringExtra("name");
        et_name.setText(name);

        // SubActivity로 이동 (webview)
        btn_carema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, SubActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 등록 날짜에 현재시간 입력
        btn_nowDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNowDate();
            }
        });

        // 유통기간에 달력 팝업 띄어줌
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragmet();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_count.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // 달력이 나올때 현재날짜와 등록날짜랑 같은 형식으로 출력
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        String currentDateString = simpleDateFormat.format(calendar.getTime());
        TextView et_date = (TextView) findViewById(R.id.et_date);
        et_date.setText(currentDateString);
    }

    // 현재시간 입력해주는 함수
    void showNowDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        Date date = new Date();
        String nowDate = simpleDateFormat.format(date);
        et_nowDate.setText(nowDate);
    }


}
