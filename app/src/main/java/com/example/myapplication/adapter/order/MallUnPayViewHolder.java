package com.example.myapplication.adapter.order;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class MallUnPayViewHolder extends OrderViewHolder {

    public RecyclerView recyclerView;

    public MallUnPayViewHolder(@NonNull View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.recycler_goods_list);
    }
}
