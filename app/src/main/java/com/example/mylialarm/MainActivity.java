package com.example.mylialarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.mylialarm.bean.Data;
import com.example.mylialarm.bean.RequestData;
import com.example.mylialarm.utils.RecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String TAG = "MainActivity";
    String connectionToCloudURL = "http://10.245.17.106:8888/";
    private OkHttpClient httpClient;
    private Retrofit connectionToCloud;
    HttpLoggingInterceptor retrofitInterceptor;
    TimeDataService connectionToCloudURLApiService;
    private RecyclerView dataListRecyclerView;
    private RecycleViewAdapter dataListRecyclerViewAdapter;
    private List<Data> recycleDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDataList();
        updateDataList();

        connectionToCloudURLApiService = getConnectionToCloudURLApiService(TimeDataService.class);
        //外观模式
    }


    public TimeDataService getConnectionToCloudURLApiService(Class<?> className){
        retrofitInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(TAG, "log_interceptor: "+message);
            }
        });
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(retrofitInterceptor)
                .build();
        connectionToCloud = new Retrofit.Builder()
                .baseUrl(connectionToCloudURL)
                .client(httpClient)
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return (TimeDataService) connectionToCloud.create(className);
    }

    private void initDataList(){
        recycleDataList = new ArrayList<>();

        recycleDataList.add(new Data("Time1",1,30));
        recycleDataList.add(new Data("Time2",2,30));
        recycleDataList.add(new Data("Time3",3,30));
        recycleDataList.add(new Data("Time4",4,30));
        recycleDataList.add(new Data("Time5",5,30));
        recycleDataList.add(new Data("Time6",6,30));
        recycleDataList.add(new Data("Time7",7,30));
        recycleDataList.add(new Data("Time8",8,30));
        recycleDataList.add(new Data("Time9",9,30));
        recycleDataList.add(new Data("Time10",10,30));
    }

    public void updateDataList(){
        dataListRecyclerViewAdapter = new RecycleViewAdapter(MainActivity.this,recycleDataList);
        dataListRecyclerView = findViewById(R.id.recycleReview);
        dataListRecyclerView.setAdapter(dataListRecyclerViewAdapter);
        //设置LayoutManager
        dataListRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
    }

    private void getDataFromCloud(){
        connectionToCloudURLApiService.getRandomTimeData().enqueue(new Callback<RequestData>() {
            @Override
            public void onResponse(Call<RequestData> call, retrofit2.Response<RequestData> response) {
                RequestData requestData = response.body();
                Log.i(TAG, "requestData onResponse: "+requestData.toString());
                recycleDataList = requestData.getData();
                updateDataList();
            }

            @Override
            public void onFailure(Call<RequestData> call, Throwable t) {
                Log.i(TAG, "获取失败");
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.update:
                getDataFromCloud();
                break;
        }
    }
}