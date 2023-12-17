package com.example.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Frag2 extends Fragment {
    private View view;
    private DBHelper mDBhelper;
    private ArrayList<ToDoItem> toDoItems;
    private ExpandableListView expandableListView;
    private ExpandableListViewAdapter expandableListViewAdapter;
    List<String> chapterList;
    HashMap<String, List<String>> topicList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2, container, false);

        expandableListView = view.findViewById(R.id.eListView);


        showList();

        expandableListViewAdapter = new ExpandableListViewAdapter(view.getContext(), chapterList, topicList);

        expandableListView.setAdapter(expandableListViewAdapter);

       /* Button updateBtn = view.findViewById(R.id.update);
        Button deleteBtn = view.findViewById(R.id.delete);

        //수정버튼 클릭시 데이터베이스 데이터 교체
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
                LayoutInflater dialogInflater = LayoutInflater.from(v.getContext());
                View dialogView = dialogInflater.inflate(R.layout.update_dialog, null);

                final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
                final TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
                final EditText alarm = (EditText) dialogView.findViewById(R.id.alarmText);
                final EditText schedule = (EditText) dialogView.findViewById(R.id.scheduleText);

                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });


                //다이얼로그 창에서 이미지뷰로 띄울 아이콘
                ad.setIcon(R.mipmap.ic_launcher);
                //다이얼 로그 창의 이미지뷰 옆에 적을 제목
                ad.setTitle("일정 시간, 알람 시간, 일정을 입력해주세요.");

                ad.setView(dialogView);

                AlertDialog alertDialog = ad.create();
                alertDialog.show();

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String scheduleValue = schedule.getText().toString();
                        // 알람일( 일) 을 가져온다
                        String alarmValue = alarm.getText().toString().trim();

                        // 데이트피커의 값을 가져온다.
                        String year = String.valueOf(datePicker.getYear());
                        String month = String.valueOf(datePicker.getMonth() + 1) ;
                        String day = String.valueOf(datePicker.getDayOfMonth());
                        if (Integer.parseInt(month) < 10) {
                            month = "0" + month;
                        }
                        if (Integer.parseInt(day) < 10) {
                            day = "0" + day;
                        }

                        String yearMonthDay = year + "-" + month + "-" + day;

                        // 타임피커의 값을 가져온다.
                        String dateHour = String.valueOf(timePicker.getHour());
                        String dateMinute = String.valueOf(timePicker.getMinute());
                        if (Integer.parseInt(dateHour) < 10) {
                            dateHour = "0" + dateHour;
                        }
                        if (Integer.parseInt(dateMinute) < 10 && dateMinute != "00") {
                            dateMinute = "0" + dateMinute;
                        }

                        String dateTime = dateHour + ":" + dateMinute;


                        if (isStrAlarm(alarmValue.trim()) == false || scheduleValue.trim().isEmpty()) {

                            if (isStrAlarm(alarmValue.trim()) == false && scheduleValue.trim().isEmpty()) {
                                Toast.makeText(v.getContext(), "알람은 숫자를, 일정은 필수로 입력해주새요.", Toast.LENGTH_SHORT).show();
                                alarm.setText("");
                            } else if (isStrAlarm(alarmValue.trim()) == false) {
                                Toast.makeText(v.getContext(), "숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                                alarm.setText("");
                            } else if (scheduleValue.trim().isEmpty()) {
                                Toast.makeText(v.getContext(), "일정을 입력해주세요.", Toast.LENGTH_SHORT).show();
                            }


                        } else {

                            String key = yearMonthDay + " " + dateTime;
                            mDBhelper.updateTodo(yearMonthDay, dateTime, alarmValue, scheduleValue, key);



                            alertDialog.dismiss();



                        }
                    }
                });
            }
        });

        // 삭제버튼 클릭시 데이터 삭제
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/

        return view;
    }


    public boolean isStrAlarm(String _alarm) {
        // 정수형으로 변환이 되면 true 안되면 false반환
        try {
            if (_alarm.equals("")) {
                _alarm = "0";
            }

            Integer.parseInt(_alarm);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    private void showList() {
        chapterList = new ArrayList<>();
        topicList = new HashMap<String, List<String>>();

        chapterList.add("예정된 일정");
        chapterList.add("완료된 일정");

        mDBhelper = new DBHelper(view.getContext());
        toDoItems = mDBhelper.getToDoList();
        Collections.reverse(toDoItems);


        List<String> progress = new ArrayList<>();
        for(int i = 0; i < toDoItems.size(); i++){
            String date = toDoItems.get(i).getDate();
            String time = toDoItems.get(i).getDateTime();
            String schedule = toDoItems.get(i).getSchedule();

            String strAlarm = toDoItems.get(i).getAlarm();
            int intAlarm = Integer.parseInt(strAlarm);

            // 날짜 형식 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate currentDate = LocalDate.parse(date, formatter);

            // 알람일자 계산
            LocalDate alarmDate = currentDate.minusDays(intAlarm);

            // 알람일자 문자열로 변환
            String alarmDateString = alarmDate.format(formatter);
            alarmDateString += "  " + time;

            if(intAlarm == 0){
                alarmDateString = "없음";
            }

            String toDO = "날짜 : " + date + "\n시간 : " + time + "\n예약 알람 : " + alarmDateString +  "\n일정 : " + schedule ;
            progress.add(toDO);
        }


        // 완료된 일정을 추가하는 리스트
        List<String> complete = new ArrayList<>();


        topicList.put(chapterList.get(0), progress);
        topicList.put(chapterList.get(1), complete);
    }
}
