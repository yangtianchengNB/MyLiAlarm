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
    String URL = "http://10.245.17.106:8888/";
    private OkHttpClient client;
    private Retrofit retrofit;
    HttpLoggingInterceptor interceptor;
    TimeDataService apiService;
    public static RequestData staticRequestData;
    private RecyclerView recyclerView;
    private RecycleViewAdapter recycleViewAdapter;
    private List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 10; i++) {
            Data data = new Data();
            data.setName("第"+i+"条消息");
            list.add(data.toString());
        }

        update();

        interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(TAG, "log_interceptor: "+message);
            }
        });
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(TimeDataService.class);

    }

    public void update(){
        recycleViewAdapter = new RecycleViewAdapter(MainActivity.this,list);
        recyclerView = findViewById(R.id.recycleReview);
        recyclerView.setAdapter(recycleViewAdapter);
        //设置LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
    }

    private void getData(){
        apiService.getRandomTimeData().enqueue(new Callback<RequestData>() {
            @Override
            public void onResponse(Call<RequestData> call, retrofit2.Response<RequestData> response) {
                RequestData requestData = response.body();
                Log.i(TAG, "requestData onResponse: "+requestData.toString());
                staticRequestData = requestData;
                list = requestData.getData().stream().map(x -> x.toString()).collect(Collectors.toList());
                for (String s : list) {
                    System.out.print(s+" ");
                }
                System.out.println();
                update();
                //MainActivity.handler.sendMessage(message);
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
                getData();
                break;
        }
    }
}