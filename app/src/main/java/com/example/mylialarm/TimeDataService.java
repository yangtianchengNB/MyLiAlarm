package com.example.mylialarm;

import com.example.mylialarm.bean.RequestData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TimeDataService {
    @GET("/openapi/lixiangdemo/timedatalist/")
    Call<RequestData> getTimeData();

    @GET("/openapi/lixiangdemo/random/timedatalist/")
    Call<RequestData> getRandomTimeData();
}
