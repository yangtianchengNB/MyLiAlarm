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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

    public List<String> list;
    private final Context context;
    private View itemView;
    public static List<String> staticList;

    public RecycleViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
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
        String data = list.get(position);
        holder.textView.setText(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
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
                if (MainActivity.staticRequestData!=null){
                    Intent intent = new Intent(itemView.getContext(), AlarmActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("getData",RecycleViewAdapter.staticList.get(getLayoutPosition()));
                    bundle.putInt("position",getLayoutPosition());
                    intent.putExtras(bundle);
                    itemView.getContext().startActivity(intent);
                }else {
                    Toast.makeText(itemView.getContext(), "请先获取数据", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
