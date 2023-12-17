package com.example.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper1 extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "SiteMange.db";

    public DBHelper1(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS SiteManagement (siteName TEXT NOT NULL, siteID TEXT NOT NULL, sitePass TEXT NOT NULL, PRIMARY KEY(siteName, siteID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {onCreate(db);}

    public void insertMange(String _siteName, String _siteID, String _sitePass){
        SQLiteDatabase db = getWritableDatabase();
        //투두 리스트 테이블 안에 데이터를 넣어준다.
        db.execSQL("INSERT INTO SiteManagement(siteName, siteID, sitePass) VALUES('" + _siteName + "', '" + _siteID + "', '" + _sitePass + "');");
    }

    public ArrayList<SiteInform> getsiteInformList() {
        ArrayList<SiteInform> siteInforms = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM SiteManagement ORDER BY siteName DESC", null);

        if(cursor.getCount() != 0){
            while (cursor.moveToNext()) {
                String siteName = cursor.getString(cursor.getColumnIndexOrThrow("siteName"));
                String siteID = cursor.getString(cursor.getColumnIndexOrThrow("siteID"));
                String sitePass = cursor.getString(cursor.getColumnIndexOrThrow("sitePass"));

                // ToDoItem 객체 생성
                SiteInform siteInform = new SiteInform();
                siteInform.setSiteName(siteName);
                siteInform.setSiteID(siteID);
                siteInform.setSitePass(sitePass);

                siteInforms.add(siteInform);// ArrayList에 추가

            }

        }
        cursor.close();
        return siteInforms;
    }


    public void updateSite(String _siteName, String _siteID, String _sitePass, String key1, String key2){

        SQLiteDatabase db = getWritableDatabase();
        //투두 리스트 테이블 안에 데이터를 넣어준다.

        db.execSQL("UPDATE SiteManagement SET siteName = '" + _siteName + "', siteID = '" + _siteID + "', sitePass = '" + _sitePass + "' WHERE siteName = '" + key1 + "' AND siteID = '" + key2 + "' ");
    }



    //테이블의 데이터 제거
    public void deletesiteInform(String _siteName, String _siteID){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM SiteManagement WHERE siteName = '" + _siteName + "' AND siteID = '" + _siteID + "' ");
    }

}
