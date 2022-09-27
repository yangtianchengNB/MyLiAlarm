package com.example.mylialarm.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylialarm.AlarmActivity;
import com.example.mylialarm.R;
import com.example.mylialarm.bean.Data;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    public List<Data> recycleDataList;
    private final Context context;
    private View itemView;

    public
    RecycleViewAdapter(Context context, List<Data> list) {
        this.context = context;
        this.recycleDataList = list;
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
                    bundle.putParcelable("getData",RecycleViewAdapter.this.recycleDataList.get(getLayoutPosition()));
                    intent.putExtras(bundle);
                    itemView.getContext().startActivity(intent);
                }
            });


        }
    }
}


