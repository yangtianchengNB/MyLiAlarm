package com.example.mylialarm;

import androidx.annotation.RequiresApi;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.mylialarm.bean.Data;
import com.example.mylialarm.bean.RequestData;
import com.example.mylialarm.utils.RecycleViewAdapter;
import com.example.mylialarm.utils.TimeDataViewModel;

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
    TimeDataViewModel timeDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeDataViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(TimeDataViewModel.class);
        initDataList();
        updateDataList();
        initConnectionToCloudURLApiService(TimeDataService.class);

    }

    /**
     * 初始化连接retrofit的对象
     * @param className
     */
    public void initConnectionToCloudURLApiService(Class<?> className){
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

        connectionToCloudURLApiService = (TimeDataService) connectionToCloud.create(className);
    }

    /**
     * 加载本地缓存的数据
     */
    private void initDataList(){
        List recycleDataList = new ArrayList<>();
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
        timeDataViewModel.getCurrentDataList().postValue(recycleDataList);
    }

    /**
     * 将recycleDataList中的数据更新到RecycleView中
     */
    public void updateDataList(){
        timeDataViewModel.getCurrentDataList().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                dataListRecyclerViewAdapter = new RecycleViewAdapter(MainActivity.this,data);
                dataListRecyclerView = findViewById(R.id.recycleReview);
                dataListRecyclerView.setAdapter(dataListRecyclerViewAdapter);
                //设置LayoutManager
                dataListRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
            }
        });

    }

    /**
     * 从云端获取数据
     */
    private void getDataFromCloud(){
        long getTimeBefore = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectionToCloudURLApiService.getRandomTimeData().enqueue(new Callback<RequestData>() {
                    @Override
                    public void onResponse(Call<RequestData> call, retrofit2.Response<RequestData> response) {
                        RequestData requestData = response.body();
                        Log.i(TAG, "requestData onResponse: "+requestData.toString());
                        timeDataViewModel.getCurrentDataList().postValue(requestData.getData());
                    }

                    @Override
                    public void onFailure(Call<RequestData> call, Throwable t) {
                        Log.i(TAG, "获取失败");
                    }
                });
            }
        }).start();

        System.out.println("getDataFromCloud耗时："+(System.currentTimeMillis() - getTimeBefore));
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.update:
                getDataFromCloud();
                break;
        }
    }
}