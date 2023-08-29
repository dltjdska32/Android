package com.example.sharedexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //임시저장을 위한 SharedPreferences
    private EditText et_save;
    String str = "file";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //xml파일의 et_save와 java파일의 et_save를 연결
        et_save = (EditText) findViewById(R.id.et_save);

        SharedPreferences sharedPreferences = getSharedPreferences(str, 0);

        //앱을 재실행 할경우 onDestroy()에서 저장된 lee라는 병명의 값을 가진 값을 꺼내온다.
        String value = sharedPreferences.getString("lee","");
        // EditText에 value의 값을 세팅함.
        et_save.setText(value);
    }


    //앱을 종료 시켰을때,  위의 액티비티를 벗어났을때 실행
    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sharedPreferences = getSharedPreferences(str, 0);
        // 저장을 할때 Editor를 생성해야함, sharedPreferences 와 editor를 연결
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //EditText의 입력값을 가져와서 문자열형태로 value에 저장
        String value = et_save.getText().toString();
        //lee라는 별명으로 value값을 저장.
        editor.putString("lee", value);
        //실질적으로 저장하는 함수.
        editor.commit();
    }
}