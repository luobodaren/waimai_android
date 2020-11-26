package com.example.myapplication.adapter.mine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.adapter.MyBaseRecyclerAdapter;

import java.util.List;

public class FunctionRecommendedRecyclerAdapter extends MyBaseRecyclerAdapter<FunctionRecommendedViewHolder> {


    public FunctionRecommendedRecyclerAdapter(int layoutResId, @Nullable List data, @NonNull List mItemViewModelList) {
        super(layoutResId, data, mItemViewModelList);
    }

    public FunctionRecommendedRecyclerAdapter(@Nullable List data, @NonNull List mItemViewModelList) {
        super(data, mItemViewModelList);
    }

    public FunctionRecommendedRecyclerAdapter(int layoutResId, @NonNull List mItemViewModelList) {
        super(layoutResId, mItemViewModelList);
    }
}
