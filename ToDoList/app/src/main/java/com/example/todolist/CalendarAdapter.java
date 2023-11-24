package com.example.todolist;

import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>{

    ArrayList<LocalDate> dayList;



    public CalendarAdapter(ArrayList<LocalDate> dayList) {
        this.dayList = dayList;

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

        if(day == null) {
            holder.dayText.setText("");
        } else {
            holder.dayText.setText(String.valueOf(day.getDayOfMonth()));
        }
        //텍스트 색상 지정(툐요일 ,일요일)
        if((position + 1) % 7 == 0) {
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

                int iYear = day.getYear();
                int iMonth = day.getMonthValue();
                int iDay = day.getDayOfMonth();

                String yearMonthDay = iYear + "년" + iMonth + "월" + iDay + "일";

                AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());

                LayoutInflater dialogInflater = LayoutInflater.from(v.getContext());
                View dialogView = dialogInflater.inflate(R.layout.dialog,null);

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

                        if(scheduleValue.trim().isEmpty()){
                            Toast.makeText(v.getContext(), "일정을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
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

    class CalendarViewHolder extends RecyclerView.ViewHolder{

        TextView dayText;
        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            dayText = itemView.findViewById(R.id.dayText);
        }
    }


}
