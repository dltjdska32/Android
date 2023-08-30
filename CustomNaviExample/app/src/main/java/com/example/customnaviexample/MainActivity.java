package com.example.customnaviexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawer);


        Button btn_open = (Button) findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //버튼을 클릭할경우 네비게이션 메뉴 열림
                drawerLayout.openDrawer(drawerView);
            }
        });

        Button btn_close = (Button)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        // 드로우어 리스너 슬라이드 했을경우 실행
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        //드로우어 리스너가 열려있을경우 실행
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        //슬라이드 메뉴가 닫혔을경우 실행
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        //상태가 바뀌었읅경우 실행
        public void onDrawerStateChanged(int newState) {

        }
    };
}