package com.example.myapplication.adapter.order;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.adapter.MyBaseRecyclerAdapter;

import java.util.List;

public class OrderListRecyclerAdapter extends MyBaseRecyclerAdapter<MallUnPayViewHolder> {
    public OrderListRecyclerAdapter(int layoutResId, @Nullable List data, @NonNull List mItemViewModelList) {
        super(layoutResId, data, mItemViewModelList);
    }

    public OrderListRecyclerAdapter(@Nullable List data, @NonNull List mItemViewModelList) {
        super(data, mItemViewModelList);
    }

    public OrderListRecyclerAdapter(int layoutResId, @NonNull List mItemViewModelList) {
        super(layoutResId, mItemViewModelList);
    }
}
