package com.example.mylialarm.utils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mylialarm.bean.Data;

import java.util.List;

public class TimeDataViewModel extends ViewModel {

    private MutableLiveData<List<Data>> currentDataList;

    public MutableLiveData<List<Data>> getCurrentDataList(){
        if (currentDataList == null){
            currentDataList = new MutableLiveData<>();
        }
        return currentDataList;
    }
}
