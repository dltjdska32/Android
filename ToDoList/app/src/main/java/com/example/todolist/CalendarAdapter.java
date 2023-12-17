package com.example.todolist;

import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TimePicker;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    ArrayList<LocalDate> dayList;



    private ArrayList<ToDoItem> toDoItems;
    private DBHelper mDBHelper;
    private OnItemListener listener;
    private MyViewModel myViewModel;

    public void setOnItemListener(OnItemListener listener) {
        this.listener = listener;
    }

    public CalendarAdapter(ArrayList<LocalDate> dayList, MyViewModel myViewModel) {
        this.dayList = dayList;
        this.myViewModel = myViewModel;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.calendar_cell, parent, false);

        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        //날짜 변수에 담기
        LocalDate day = dayList.get(position);

        if (day == null) {
            holder.dayText.setText("");
        } else {
            holder.dayText.setText(String.valueOf(day.getDayOfMonth()));
        }
        //텍스트 색상 지정(툐요일 ,일요일)
        if ((position + 1) % 7 == 0) {
            //토요일
            holder.dayText.setTextColor(Color.BLUE);
        } else if (position == 0 || position % 7 == 0) {
            //일요일
            holder.dayText.setTextColor(Color.RED);
        }

        //날짜 클릭시 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String sYear = String.valueOf(day.getYear());
                String sMonth = String.valueOf(day.getMonthValue());
                String sDay = String.valueOf(day.getDayOfMonth());

                if (Integer.parseInt(sYear) < 10) {
                    sYear = "0" + sYear;
                }
                if (Integer.parseInt(sMonth) < 10) {
                    sMonth = "0" + sMonth;
                }
                if (Integer.parseInt(sDay) < 10) {
                    sDay = "0" + sDay;
                }

                String yearMonthDay = sYear + "-" + sMonth + "-" + sDay ;

                AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());

                LayoutInflater dialogInflater = LayoutInflater.from(v.getContext());
                View dialogView = dialogInflater.inflate(R.layout.dialog, null);

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

                        boolean b = false;

                       mDBHelper = new DBHelper(v.getContext());
                       toDoItems = mDBHelper.getToDoList();



                        for (int i = 0; i < toDoItems.size(); i++) {
                            Log.d("CalendarAdapter", "Comparing: " + toDoItems.get(i).getDate() + " with " + yearMonthDay
                                    + ", " + toDoItems.get(i).getDateTime() + " with " + dateTime);


                            if (toDoItems.get(i).getDate().equals(yearMonthDay) && toDoItems.get(i).getDateTime().equals(dateTime)) {
                                b = true;
                                break;
                            }
                        }

                        if (isStrAlarm(alarmValue.trim()) == false || scheduleValue.trim().isEmpty() || b == true) {

                            if (isStrAlarm(alarmValue.trim()) == false && scheduleValue.trim().isEmpty()) {
                                Toast.makeText(v.getContext(), "알람은 숫자를, 일정은 필수로 입력해주새요.", Toast.LENGTH_SHORT).show();
                                alarm.setText("");
                            } else if (isStrAlarm(alarmValue.trim()) == false) {
                                Toast.makeText(v.getContext(), "숫자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                                alarm.setText("");
                            } else if (scheduleValue.trim().isEmpty()) {
                                Toast.makeText(v.getContext(), "일정을 입력해주세요.", Toast.LENGTH_SHORT).show();
                            } else if (b) {
                                Toast.makeText(v.getContext(), "해당 날짜와 시간에 일정이 있습니다.", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            // 일정 날짜 , 일정 시간, 알람시간, 일정 등 todoList.db에 담는다.
                            mDBHelper = new DBHelper(v.getContext());


                            if (alarmValue.equals("") || alarmValue == null) {
                                alarmValue = "0";
                            }
                            // yearMonthDay (date값) , dateTime(dateTime 값) ,  alarmValue ( alarm값), scheduleValue (schedule값)

                            // 사용자의 입력 데이터 할일리스트 데이터베이스에 추가
                            mDBHelper.insertTodo(yearMonthDay, dateTime, alarmValue, scheduleValue);
                            mDBHelper = new DBHelper(v.getContext());
                            toDoItems = mDBHelper.getToDoList();
                            String DBsize = String.valueOf(toDoItems.size());

                            Log.d("lsn", "onCreateView: " +toDoItems.size());

                            myViewModel.setDBsize(DBsize);


//                            if (listener != null) {
//                                toDoItems = mDBHelper.getToDoList();
//
//                                String dbSize = String.valueOf(toDoItems.size());
//
//                                listener.onItemClick(dbSize);
//                            }

                            alertDialog.dismiss();



                        }
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return dayList.size();
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


    class CalendarViewHolder extends RecyclerView.ViewHolder {

        TextView dayText;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            dayText = itemView.findViewById(R.id.dayText);

        }
    }




}
