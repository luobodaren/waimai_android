package com.example.myapplication.adapter.mine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.adapter.MyBaseRecyclerAdapter;

import java.util.List;

public class FoodTypeRecyclerAdapter extends MyBaseRecyclerAdapter<FoodTypeRecyclerViewHolder> {
    public FoodTypeRecyclerAdapter(int layoutResId, @Nullable List data, @NonNull List mItemViewModelList) {
        super(layoutResId, data, mItemViewModelList);
    }

    public FoodTypeRecyclerAdapter(@Nullable List data, @NonNull List mItemViewModelList) {
        super(data, mItemViewModelList);
    }

    public FoodTypeRecyclerAdapter(int layoutResId, @NonNull List mItemViewModelList) {
        super(layoutResId, mItemViewModelList);
    }
}
