package com.example.imageviewtoast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // findViewById는 test라는 아이디를 찾아온다.
        // R(res 라는 리소스파일에서) . id( id가). test(test인 것) 을 찾아온다.
        test = findViewById(R.id.test);

        //test라는 이미지뷰를 클릭하면 실행
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //토스트 메세지를 팝업으로 송출
                // Toast.makeText(getApplicationContext(), "본인원하는글자", Toast.LENGTH_SHORT(짧게 토스트 메세지 표출) || Toast.LENGTH_LONG(길게 표출)).show();
                //getApplicationContext() -> MainActivity를 뜻함
                Toast.makeText(getApplicationContext(), "안드로이드 개발해보자.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}