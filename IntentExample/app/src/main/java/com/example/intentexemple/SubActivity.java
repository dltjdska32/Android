package com.example.intentexemple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    private TextView tv_sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        tv_sub = findViewById(R.id.tv_sub);


        //intent 생성 -> 어디선가 intent 값이 날라오면 subActivity에 생성된 intent에서 값을 받는다.
        Intent intent = getIntent();
        // 문자열 str에  MainActivity에서 담아서온 문자열 str에 저장된 값을 받는다
        // -> getStringExtra("str")의 str은 MainActivity에서 만든 str의 별명. -> 별명이 같아야함 sub와 main모두
        String str =  intent.getStringExtra("str");

        //str 데이터를 sub의  textView에 쓴다.
        tv_sub.setText(str);
    }
}