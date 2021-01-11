package com.life.waimaishuo.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;

import java.util.List;

/**
 * mvvm 实现界面与业务分离
 * @param <T>
 */
public class MyBaseRecyclerAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    private int variableId;

    public MyBaseRecyclerAdapter(int layoutResId, @Nullable List data) {
        this(layoutResId, data, -1);
    }

    public MyBaseRecyclerAdapter(int layoutResId, @Nullable List data, int variableId) {
        super(layoutResId, data);
        this.variableId = variableId;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, T item) {
        //dataBinding
        if(variableId != -1)
            DataBindingUtil.bind(holder.itemView).setVariable(variableId,item);
        //特殊需求通过此方法实现
        initView(holder,item);

        //屏幕适配
        if(holder.itemView instanceof ViewGroup){
            UIUtils.getInstance(mContext).autoAdapterUI((ViewGroup)holder.itemView);
        }else {
            UIUtils.getInstance(mContext).autoAdapterUI(holder.itemView);
        }
    }


    @Override
    public int addHeaderView(View header, int index, int orientation) {
        int result = super.addHeaderView(header, index, orientation);
        if(header instanceof ViewGroup){
            UIUtils.getInstance(mContext).autoAdapterUI((ViewGroup)header);
        }else {
            UIUtils.getInstance(mContext).autoAdapterUI(header);
        }
        return result;
    }

    @Override
    public int addFooterView(View footer, int index, int orientation) {
        int result = super.addFooterView(footer, index, orientation);
        if(footer instanceof ViewGroup){
            UIUtils.getInstance(mContext).autoAdapterUI((ViewGroup)footer);
        }else {
            UIUtils.getInstance(mContext).autoAdapterUI(footer);
        }
        return result;
    }

    protected void initView(BaseViewHolder helper,T item){ };

}
