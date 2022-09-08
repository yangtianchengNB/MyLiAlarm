package com.example.mylialarm.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylialarm.AlarmActivity;
import com.example.mylialarm.MainActivity;
import com.example.mylialarm.R;
import com.example.mylialarm.bean.Data;
import com.example.mylialarm.bean.RequestData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

    public List<Data> recycleDataList;
    private final Context context;
    private View itemView;
    public static List<Data> staticList;

    public RecycleViewAdapter(Context context, List<Data> list) {
        this.context = context;
        this.recycleDataList = list;
        staticList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = View.inflate(context, R.layout.item_layout,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String data = recycleDataList.get(position).toString();
        holder.textView.setText(data);
    }

    @Override
    public int getItemCount() {
        return recycleDataList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView textView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.string);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), AlarmActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("getData",RecycleViewAdapter.staticList.get(getLayoutPosition()));
                intent.putExtras(bundle);
                itemView.getContext().startActivity(intent);
            }
        });


    }
}
