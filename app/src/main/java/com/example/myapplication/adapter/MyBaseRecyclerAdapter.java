package com.example.myapplication.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.base.utils.LogUtil;
import com.example.base.utils.UIUtils;
import com.example.myapplication.MyApplication;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;

import java.util.List;

public abstract class MyBaseRecyclerAdapter<T extends BaseViewHolder> extends BaseQuickAdapter<Object, T> {

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
        int position = getData().indexOf(item);

        BaseRecyclerViewModel vm= getItemViewModel(position);
        if(vm != null){
            vm.bindModel( helper,vm.getModel(),this);
        }

        //屏幕适配
        if(helper.itemView instanceof ViewGroup){
            UIUtils.autoAdapterUI(MyApplication.getMyApplication(),(ViewGroup)helper.itemView);
        }else {
            UIUtils.autoAdapterUI(MyApplication.getMyApplication(),helper.itemView);
        }
    }

    public BaseRecyclerViewModel getItemViewModel(int index) {
        if (index < getItemCount()){
            if(mItemViewModelList != null && mItemViewModelList.size()>index)
                return mItemViewModelList.get(index);
        }
        return null;
    }

    @Override
    public int addHeaderView(View header, int index, int orientation) {
        int result = super.addHeaderView(header, index, orientation);
        if(header instanceof ViewGroup){
            UIUtils.autoAdapterUI(MyApplication.getMyApplication(),(ViewGroup)header);
        }else {
            UIUtils.autoAdapterUI(MyApplication.getMyApplication(),header);
        }
        return result;
    }

    @Override
    public int addFooterView(View footer, int index, int orientation) {
        int result = super.addFooterView(footer, index, orientation);
        if(footer instanceof ViewGroup){
            UIUtils.autoAdapterUI(MyApplication.getMyApplication(),(ViewGroup)footer);
        }else {
            UIUtils.autoAdapterUI(MyApplication.getMyApplication(),footer);
        }
        return result;
    }

    public List<BaseRecyclerViewModel> getmItemViewModelList() {
        return mItemViewModelList;
    }


}
