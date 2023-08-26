package com.example.intentexemple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    private Button btn_move;
    private EditText et_test;
    private String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //현제 자바파일과 xml 파일을 연결해라
        setContentView(R.layout.activity_main);

        //java파일의 et_test와 xml 파일의 et_test를 연결
        et_test = findViewById(R.id.et_test);


        //java파일 btn_move 와 xml 파일 btn_move를 연결
        btn_move = findViewById(R.id.btn_move);

        //btn_move를 클릭시 실행
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            //btn_move버튼 클릭시 실행되는 로직
            public void onClick(View view) {

                //문자열 str에 EditText에 입력된 값을 가져와 담는다.
                // 그냥 et_test.getText(); 만입력할경우 오류 -> 문자열 형태가 아니기 때문에
                // toString() 함수를 사용해준다.
                str = et_test.getText().toString();
                //intent를 생성  -> intent(현재액티비티, 이동하고싶은 액티비티)
                Intent intent = new Intent(MainActivity.this, SubActivity.class);

                //인텐트안에 문자열 str을 실는다.
                intent.putExtra("str" /*str의 별명과 같음, subActivity에서 받기 위해*/, str/*실제로 입력된 값이 저장된 문자열*/);

                //액티비티 이동해주는 함수, subActivity로 intent를 이동한다.
                startActivity(intent);
            }
        });
    }
}