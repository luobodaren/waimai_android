package com.example.myapplication.adapter.mine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.adapter.MyBaseRecyclerAdapter;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;

import java.util.List;

public class TopDataRecyclerAdapter extends MyBaseRecyclerAdapter<TopDataRecyclerViewHolder> {
    public TopDataRecyclerAdapter(int layoutResId, @Nullable List data, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
        super(layoutResId, data, mItemViewModelList);
    }

    public TopDataRecyclerAdapter(@Nullable List data, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
        super(data, mItemViewModelList);
    }

    public TopDataRecyclerAdapter(int layoutResId, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
        super(layoutResId, mItemViewModelList);
    }

}
