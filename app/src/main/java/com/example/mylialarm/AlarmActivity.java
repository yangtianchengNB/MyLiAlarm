package com.example.mylialarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylialarm.bean.Data;
import com.example.mylialarm.bean.RequestData;

public class AlarmActivity extends AppCompatActivity {

    Button back;
    ImageView hour,minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        back = findViewById(R.id.toBackActivity);
        hour = findViewById(R.id.iv_hour);
        minute = findViewById(R.id.iv_minute);

        Bundle bundle = this.getIntent().getExtras();
        RequestData requestData = MainActivity.staticRequestData;
        int key = bundle.getInt("position");
        Data data = requestData.getData().get(key);
        int hour = data.getHour();
        int min = data.getMin();
        System.out.println(data.toString());
//        String string = bundle.getString("getData");
//        textView.setText(requestData.getData().get(key).toString());

        RotateAnimation hourAnimation = new RotateAnimation(0,(hour%12)*30, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        hourAnimation.setDuration(5000);
        hourAnimation.setFillAfter(true);
        this.hour.startAnimation(hourAnimation);

        RotateAnimation minuteAnimation = new RotateAnimation(0,min*6, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        minuteAnimation.setDuration(5000);
        minuteAnimation.setFillAfter(true);
        minute.startAnimation(minuteAnimation);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toBackActivity:
                finish();
        }
    }
}