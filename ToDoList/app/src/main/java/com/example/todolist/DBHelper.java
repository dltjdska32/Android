    package com.example.todolist;

    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;

    import androidx.annotation.Nullable;

    import java.util.ArrayList;

    public class DBHelper extends SQLiteOpenHelper {

        private static final int DB_VERSION = 1;
        private static final String DB_NAME = "ToDoList.db";

        public DBHelper(@Nullable Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //DB생성시 호출
            // 테이블을 만든다 테이블이 존재하지 않다면.
            //테이블의 헤더 (년월일(날짜) , 일정 시간, 알람시간, 일정)
            db.execSQL("CREATE TABLE IF NOT EXISTS ToDoList (date TEXT NOT NULL, dateTime TEXT NOT NULL, alarm TEXT, schedule TEXT NOT NULL, PRIMARY KEY(date, dateTime))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db);
        }

        //테이블에 데이터추가
        public void insertTodo(String _date, String _dateTime, String _alarm, String _schedule){
            SQLiteDatabase db = getWritableDatabase();
            //투두 리스트 테이블 안에 데이터를 넣어준다.
            db.execSQL("INSERT INTO ToDoList(date, dateTime, alarm, schedule) VALUES('" + _date + "', '" + _dateTime + "', '" + _alarm + "', '" + _schedule + "');");
        }



        //SELECT문 목록 조회(데이터 베이스에 있는 내용을 어레이리스트로 변환)
        public ArrayList<ToDoItem> getToDoList() {
            ArrayList<ToDoItem> toDoItems = new ArrayList<>();

            SQLiteDatabase db = getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM ToDoList ORDER BY date DESC ,dateTime DESC", null);

            if(cursor.getCount() != 0){
                while (cursor.moveToNext()) {
                    String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                    String dateTime = cursor.getString(cursor.getColumnIndexOrThrow("dateTime"));
                    String alarm = cursor.getString(cursor.getColumnIndexOrThrow("alarm"));
                    String schedule = cursor.getString(cursor.getColumnIndexOrThrow("schedule"));

                    // ToDoItem 객체 생성
                    ToDoItem toDoItem = new ToDoItem();
                    toDoItem.setDate(date);
                    toDoItem.setDateTime(dateTime);
                    toDoItem.setAlarm(alarm);
                    toDoItem.setSchedule(schedule);

                    // ArrayList에 추가
                    toDoItems.add(toDoItem);
                }

            }
            cursor.close();
            return toDoItems;
        }






        //테이블의 데이터 갱신
        public void updateTodo(String _date, String _dateTime, String _alarm, String _schedule, String key1, String key2){

            SQLiteDatabase db = getWritableDatabase();
            //투두 리스트 테이블 안에 데이터를 넣어준다.
            db.execSQL("UPDATE ToDoList SET date = '" + _date + "', dateTime = '" + _dateTime + "', alarm = '" + _alarm + "', schedule = '" + _schedule + "' WHERE date = '" + key1 + "' AND dateTime = '" + key2 + "' ");
        }



        //테이블의 데이터 제거
        public void deleteToDo(String _date, String _dateTime){
            SQLiteDatabase db = getWritableDatabase();

            db.execSQL("DELETE FROM ToDoList WHERE date = '" + _date + "' AND dateTime = '" + _dateTime + "' ");
        }





    }
