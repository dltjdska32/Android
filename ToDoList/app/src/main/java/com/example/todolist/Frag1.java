package com.example.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Frag1 extends Fragment {

    TextView monthYearText; //년월 텍스트부
    LocalDate selectedDate; //년월 변수
    RecyclerView recyclerView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // xml파일과 연결
        view = inflater.inflate(R.layout.frag1, container, false);

        monthYearText = view.findViewById(R.id.monthYearText);
        ImageButton preBtn = view.findViewById(R.id.pre_btn);
        ImageButton nextBtn = view.findViewById(R.id.next_btn);
        recyclerView = view.findViewById(R.id.recyclerView);
        //현재날짜
        selectedDate = LocalDate.now();

        //화면 설정
        setMonthview();

        //이전달 버튼 이벤트
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // - 1 한 달을 넣어줌
                selectedDate = selectedDate.minusMonths(1);
                setMonthview();
            }
        });

        //다음달 버튼 이벤트
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // + 1 한 달을 넣어줌
                selectedDate = selectedDate.plusMonths(1);
                setMonthview();
            }
        });

        return view;
    }

    //날짜 타입 설정
    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 yyyy");
        return date.format(formatter);
    }



    // 다른 메소드에서 호출할 수 있는 화면 설정 메소드
    private void setMonthview() {
        //년월 텍스트뷰 셋팅
        monthYearText.setText(monthYearFromDate(selectedDate));
        //해당 월 날짜 가져오기
        ArrayList<LocalDate> dayList = daysInMonthArray(selectedDate);
        // 어뎁터 데이터 적용
        CalendarAdapter adapter = new CalendarAdapter(dayList);

        // 레이아웃 설정 열 7
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        //레이아웃 적용
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapter);
    }

    private ArrayList<LocalDate> daysInMonthArray(LocalDate date){

        ArrayList<LocalDate> dayList = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        //해당 월 마지막 날짜 가져오기
        int lastDay = yearMonth.lengthOfMonth();
        //해당월 첮째날 가져오기
        LocalDate firstDay = selectedDate.withDayOfMonth(1);
        //첫번째 날 요일 가져오기
        int dayOfWeek = firstDay.getDayOfWeek().getValue();

        //날짜 생성
        for(int i = 1; i < 42; i++){
            if (i <= dayOfWeek || i > lastDay + dayOfWeek){
                dayList.add(null);
            } else{
                dayList.add(LocalDate.of(selectedDate.getYear(),
                        selectedDate.getMonth(), i - dayOfWeek));
            }
        }

        return dayList;
    }


}
