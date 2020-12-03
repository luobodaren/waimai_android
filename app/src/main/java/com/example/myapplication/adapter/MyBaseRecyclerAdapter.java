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

public abstract class MyBaseRecyclerAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    protected List<BaseRecyclerViewModel> mItemViewModelList;


    public MyBaseRecyclerAdapter(@Nullable List data, List<BaseRecyclerViewModel> mItemViewModelList) {
        super(data);
        this.mItemViewModelList = mItemViewModelList;
    }

    public MyBaseRecyclerAdapter(int layoutResId, List<BaseRecyclerViewModel> mItemViewModelList) {
        super(layoutResId);
        this.mItemViewModelList = mItemViewModelList;
    }

    public MyBaseRecyclerAdapter(int layoutResId, @Nullable List data, List<BaseRecyclerViewModel> mItemViewModelList) {
        super(layoutResId, data);
        this.mItemViewModelList = mItemViewModelList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, T item) {
        int headCount = getHeaderLayoutCount();
        int position = getData().indexOf(item);

        BaseRecyclerViewModel vm= getItemViewModel(position);

        //分离界面控制与业务逻辑
        initView(holder,item);
        if(vm != null){ //若不传入viewModel 则直接在initView中处理数据
            vm.bindModel( holder,vm.getModel(),this);
        }

        //屏幕适配
        if(holder.itemView instanceof ViewGroup){
            UIUtils.autoAdapterUI(MyApplication.getMyApplication(),(ViewGroup)holder.itemView);
        }else {
            UIUtils.autoAdapterUI(MyApplication.getMyApplication(),holder.itemView);
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

    protected abstract void initView(BaseViewHolder helper,T item);

}
