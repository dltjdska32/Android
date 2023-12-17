package com.example.todolist;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MyViewModel extends ViewModel {
    private ArrayList<ToDoItem> toDoItems;
    private DBHelper mDBHelper;
    private MutableLiveData<String> DBsize = new MutableLiveData<>();

    public void setDBsize(String size){
        DBsize.setValue(size);
    }
    public LiveData<String> getDBsize() {
        return DBsize;
    }


}
