package com.example.myapplication.adapter.mine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.adapter.MyBaseRecyclerAdapter;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;

import java.util.List;

public class GoodsLogisticsRecyclerAdapter extends MyBaseRecyclerAdapter<GoodsLogisticsRecyclerViewHolder> {


    public GoodsLogisticsRecyclerAdapter(int layoutResId, @Nullable List data, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
        super(layoutResId, data, mItemViewModelList);
    }

    public GoodsLogisticsRecyclerAdapter(@Nullable List data, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
        super(data, mItemViewModelList);
    }

    public GoodsLogisticsRecyclerAdapter(int layoutResId, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
        super(layoutResId, mItemViewModelList);
    }

}
