package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    // FragmentMangager 와 FragmentTranseation은
    // 프레임레이아웃 에서 플래그들을 동적으로 교체하기위해 사용되는 도구들
    // FragmentManager는 플래그먼트의 추가 제거 교체등 여러 기능을 담당하는 클래스 하지만 직접 적용불가
    // FragmentTransaction은  FragmentManager의 여러 기능을 정의하고 직접 적용
    private FragmentManager fm;
    private FragmentTransaction ft;
    // 홈화면
    private Frag1 frag1;
    // 일정 화면
    private Frag2 frag2;
    // 관리 화면 (사이트 아이디 ~ 비밀번호 / 계좌번호 - 비밀번호 등 적을 화면)
    private Frag3 frag3;





    // 어플 생명주기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            // 바텀네비의 아이템을 선택할때 그에 맞는 플래그 호출 ( 홈 - setFrag(0) / 일정 - setFrag(1) / 관리 - setFrag(2) )
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_home) {
                    setFrag(0);
                } else if (item.getItemId() == R.id.action_assignment) {
                    setFrag(1);
                } else if (item.getItemId() == R.id.action_manage) {
                    setFrag(2);
                }


                return true;
            }
        });

        //플래그 객체들을 생성
        frag1 = new Frag1();
        frag2 = new Frag2();
        frag3 = new Frag3();

        setFrag(0); //첫 프래그먼트 화면을 무엇으로 지정해 줄 것인지 선택 (시작시 홈으로 시작)
        bottomNavigationView.setSelectedItemId(R.id.action_home); // 선택된 바텀네비의 아이템 변경 (시작할때 홈으로 시작.)
    }

    //프래그 먼트 교체하는 실행문
    private void setFrag(int n) {
        // fragmentManger를 불러와서 fm에 할당
        fm = getSupportFragmentManager();
        // FragmentTransaction을 사용하기 위해서 beginTransaction()사용
        ft = fm.beginTransaction();

        switch (n) {
            case 0:
                //replace() 함수는 매개변수 2개를 받음 첫번째 매개변수는 변경할위치(프레임레이아웃),
                // 두번째 매개변수 변경할 플래그(fragX) 변경할 위치에 플래그먼트를 대체한다.
                ft.replace(R.id.main_frame, frag1);
                //변경한 값을 적용한다.
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, frag2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, frag3);
                ft.commit();
                break;

        }
    }
}