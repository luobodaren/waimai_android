package com.example.myapplication.adapter.mine;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.adapter.RecyclerViewHolder.MyBaseRecyclerViewHolder;

public class TopDataRecyclerViewHolder extends MyBaseRecyclerViewHolder {

    public TextView tv_data_type;
    public TextView tv_data_count;
    public TextView rightInterval;

    public TopDataRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_data_type = itemView.findViewById(R.id.tv_data_type);
        tv_data_count = itemView.findViewById(R.id.tv_data_count);
        rightInterval = itemView.findViewById(R.id.right_interval);
    }
}
