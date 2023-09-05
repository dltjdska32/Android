package com.example.threadandhandlerexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn_start, btn_stop;
    Thread thread;
    private  boolean isThread = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewById는 View객체를 취급한다. 따라서 (Button)형변환을 해준다.
        //하지만 현재는 Android Jetpack이라는 라이브러리와 같은 도구들로 인해 개발이 간소화 되어
        //형변환 필요가없다.
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);

        //btn_start (스레드 시작)버튼을 누를 경우 실행된다.
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isThread = true;

                thread = new Thread(){
                    public void run(){
                        //btn_stop 버튼을 누르기 전까지 계속실행

                        // 5초쉬고 토스트메세지 보내고를 반복한다.
                        while(isThread){
                            //hadler 클래스의 hendleMassage를 호출
                            handler.sendEmptyMessage(0);
                            // 5초동안 쉰다.
                            try {
                                sleep(5000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                };
                //스레드 시작 버튼을 클릭시 스레드 시작.
                thread.start();
            }
        });

        //btn_stop (스레드 종료)버튼을 누를 경우 실행된다.
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //isThread 를 false 로 만들어서 와일문을 탈출한다.
                isThread = false;
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Toast.makeText(getApplicationContext(), "나 잔다.~ ", Toast.LENGTH_SHORT).show();
        };
    };
}