package com.example.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MessageRecyclerAdapter extends MyBaseRecyclerAdapter{

    public MessageRecyclerAdapter(int layoutResId, @Nullable List data, @NonNull List mItemViewModelList) {
        super(layoutResId, data, mItemViewModelList);
    }

    public MessageRecyclerAdapter(@Nullable List data, @NonNull List mItemViewModelList) {
        super(data, mItemViewModelList);
    }

    public MessageRecyclerAdapter(int layoutResId, @NonNull List mItemViewModelList) {
        super(layoutResId, mItemViewModelList);
    }
}
