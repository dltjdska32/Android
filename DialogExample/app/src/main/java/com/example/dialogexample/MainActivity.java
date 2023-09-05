package com.example.dialogexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn_dialog;
    TextView tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_dialog = (Button) findViewById(R.id.btn_dialog);
        tv_result = (TextView) findViewById(R.id.tv_result);

        //다이얼 로그 버튼을 누를 경우 실행
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 다이얼 로그 객체를 생성한다.
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);

                //다이얼로그 창에서 이미지뷰로 띄울 아이콘
                ad.setIcon(R.mipmap.ic_launcher);

                //다이얼 로그 창의 이미지뷰 옆에 적을 제목
                ad.setTitle("제목");
                // 다이얼 로그 창의 적을 메세지.
                ad.setMessage("이성남은 존잘인가요?");

                // EditText 객체를 생성한다.
                final EditText et = new EditText(MainActivity.this);
                //EditText를 다이얼로그에 보여준다.
                ad.setView(et);

                //다이얼 로그의 긍정버튼을 생성하고 이름을 확인으로 한다.
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // EditText에 입력된 값을 result에 문자열 형태로 저장한다.
                        String result = et.getText().toString();

                        //메인액티비티 버튼 옆의 텍스트뷰를 다이얼로그창의 EditText에 입력된 값인 result롤 변경한다.
                        tv_result.setText(result);

                        // 다이얼로그 창을 닫는다.
                        dialogInterface.dismiss();
                    }
                });

                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //다이얼로그 창을 닫는다.
                        dialogInterface.dismiss();
                    }
                });
                //다이얼로그를 표시
                ad.show();
            }
        });
    }
}