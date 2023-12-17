package com.example.todolist;

import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public<T extends  ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Factory Runtime Error");
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException("Runtime Error");
        }
    }
}
