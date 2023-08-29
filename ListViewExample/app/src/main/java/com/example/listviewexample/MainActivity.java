package com.example.listviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);

        List<String> data = new ArrayList<>();

        //어레이 리스트와 리스트 뷰를 연결하기 위한 ArrayAdapter<>
        //ArrayAdapter<>( this(현재 액티비티에 해당), 리스트뷰 디자인, 리스트)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,data);

        //리스트뷰에 연결하기위해 만든 ArrayAdapter를 연결
        list.setAdapter(adapter);

        data.add("이성남");
        data.add("안드로이드");
        data.add("감자");

        //데이터에 추가한 내용을 저장하는 함수.
        adapter.notifyDataSetChanged();;
    }
}