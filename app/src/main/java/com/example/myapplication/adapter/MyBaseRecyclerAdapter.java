package com.example.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.base.utils.LogUtil;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;

import java.util.List;

public abstract class MyBaseRecyclerAdapter<T extends MyBaseRecyclerViewHolder> extends BaseQuickAdapter<Object, T> {

    protected List<BaseRecyclerViewModel> mItemViewModelList;

    public MyBaseRecyclerAdapter(int layoutResId, @Nullable List data, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
        super(layoutResId, data);
        this.mItemViewModelList = mItemViewModelList;
    }

    public MyBaseRecyclerAdapter(@Nullable List data, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
        super(data);
        this.mItemViewModelList = mItemViewModelList;
    }

    public MyBaseRecyclerAdapter(int layoutResId, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
        super(layoutResId);
        this.mItemViewModelList = mItemViewModelList;
    }

    @Override
    protected void convert(@NonNull T helper, Object item) {
        int headCount = getHeaderLayoutCount();
        int position = getData().indexOf(item)-headCount;
        LogUtil.e("position=" + position + " headCount=" + headCount);
        BaseRecyclerViewModel vm= getItemViewModel(position);
        vm.bindMode( helper,vm.getModel(),this);
    }

    public BaseRecyclerViewModel getItemViewModel(int index) {
        if (index < getItemCount())
            return mItemViewModelList.get(index);
        return null;
    }

    public List<BaseRecyclerViewModel> getmItemViewModelList() {
        return mItemViewModelList;
    }


}
