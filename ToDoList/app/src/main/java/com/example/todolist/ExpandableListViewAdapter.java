package com.example.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> chapterList;
    private HashMap<String, List<String>> topicsList;

    Log log;
    public ExpandableListViewAdapter(Context context, List<String> chapterList, HashMap<String, List<String>> topicsList) {
        this.context = context;
        this.chapterList = chapterList;
        this.topicsList = topicsList;
    }

    @Override
    public int getGroupCount() {
        return this.chapterList.size();
    }

    //사이즈 만큼 생성
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.topicsList.get(this.chapterList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.chapterList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.topicsList.get(this.chapterList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String chapterTitle = (String) getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chapter_list, null);
        }

        TextView chapterTv = convertView.findViewById(R.id.chapter_tv);
        chapterTv.setText(chapterTitle);

        return convertView;
    }



    public void setTopicsList(HashMap<String, List<String>> newTopicsList) {
        this.topicsList = newTopicsList;
        notifyDataSetChanged(); // 데이터가 변경되었음을 어댑터에게 알림
    }

    // 사이즈만큼 함수가 돌음
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String topicTitle = (String) getChild(groupPosition, childPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.topics_list, null);
        }
////////

        DBHelper mDBhelper = new DBHelper(convertView.getContext());
        Button updateBtn = convertView.findViewById(R.id.update);
        Button deleteBtn = convertView.findViewById(R.id.delete);
        ArrayList<ToDoItem> toDoItems = new ArrayList<>();

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
           //수정
                            // 현재 자식 데이터 가져오기
                            String clickedText = topicsList.get(chapterList.get(groupPosition)).get(childPosition);

                            // 클릭한 텍스트에 대한 처리를 수행합니다.
                            Log.d("lsnclick", clickedText);

                            // 정규식 패턴
                            String pattern = "날짜 : (\\d{4}-\\d{2}-\\d{2})\\n시간 : (\\d{2}:\\d{2})";

                            // 패턴에 맞는 문자열 추출
                            Pattern r = Pattern.compile(pattern);
                            Matcher matcher = r.matcher(clickedText);

                            String extractedDate = "";
                            String extractedTime = "";
                            if (matcher.find()) {
                                // 날짜와 시간 값을 추출
                                extractedDate = matcher.group(1);
                                extractedTime = matcher.group(2);

                                // 결과 출력
                                System.out.println("날짜: " + extractedDate);
                                System.out.println("시간: " + extractedTime);
                            } else {
                                System.out.println("패턴 일치 없음");
                            }

                            if (alarmValue.equals("") || alarmValue == null) {
                                alarmValue = "0";
                            }

                            String key = extractedDate;
                            String key2 = extractedTime;
                            mDBhelper.updateTodo(yearMonthDay, dateTime, alarmValue, scheduleValue, key, key2);

                            int currentGroupPosition = groupPosition;
                            int currentChildPosition = childPosition;

                            List<String> currentTopicList = topicsList.get(chapterList.get(currentGroupPosition));
                            if(alarmValue.equals("0") || alarmValue == null){
                                alarmValue = "없음";
                            }

                            String modifiedData = "날짜 : " + yearMonthDay + "\n" + "시간 : " + dateTime + "\n예약 알람 : " + alarmValue + "\n일정 : " + scheduleValue;

                            currentTopicList.set(currentChildPosition, modifiedData);  // 수정된 데이터를 설정
                            topicsList.put(chapterList.get(currentGroupPosition), currentTopicList);
                            notifyDataSetChanged();


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
                // 현재 자식 데이터 가져오기
                String clickedText = topicsList.get(chapterList.get(groupPosition)).get(childPosition);

                // 클릭한 텍스트에 대한 처리를 수행합니다.
                Log.d("lsnclick", clickedText);

                // 정규식 패턴
                String pattern = "날짜 : (\\d{4}-\\d{2}-\\d{2})\\n시간 : (\\d{2}:\\d{2})";

                // 패턴에 맞는 문자열 추출
                Pattern r = Pattern.compile(pattern);
                Matcher matcher = r.matcher(clickedText);

                String extractedDate = "";
                String extractedTime = "";
                if (matcher.find()) {
                    // 날짜와 시간 값을 추출
                    extractedDate = matcher.group(1);
                    extractedTime = matcher.group(2);

                    // 결과 출력
                    System.out.println("날짜: " + extractedDate);
                    System.out.println("시간: " + extractedTime);
                } else {
                    System.out.println("패턴 일치 없음");
                }

                String key = extractedDate;
                String key2 = extractedTime;

                mDBhelper.deleteToDo(key, key2);
                int currentGroupPosition = groupPosition;
                int currentChildPosition = childPosition;

                List<String> currentTopicList = topicsList.get(chapterList.get(currentGroupPosition));
                currentTopicList.remove(currentChildPosition);
                notifyDataSetChanged();
            }
        });
///////////
        TextView topicTv = convertView.findViewById(R.id.topics_tv);
        topicTv.setText(topicTitle);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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


}
