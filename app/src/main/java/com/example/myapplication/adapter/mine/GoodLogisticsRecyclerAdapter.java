package com.example.myapplication.adapter.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapplication.R;
import com.example.myapplication.adapter.MyBaseRecyclerAdapter;
import com.example.myapplication.adapter.MyBaseRecyclerViewHolder;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;

import java.util.List;

public class GoodLogisticsRecyclerAdapter extends MyBaseRecyclerAdapter<GoodLogisticsRecyclerViewHolder> {


    public GoodLogisticsRecyclerAdapter(int layoutResId, @Nullable List data, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
        super(layoutResId, data, mItemViewModelList);
    }

    public GoodLogisticsRecyclerAdapter(@Nullable List data, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
        super(data, mItemViewModelList);
    }

    public GoodLogisticsRecyclerAdapter(int layoutResId, @NonNull List<BaseRecyclerViewModel> mItemViewModelList) {
        super(layoutResId, mItemViewModelList);
    }

    class ViewHolder extends BaseViewHolder{
        ImageView iv_icon;
        TextView tv_icon_type;
        public ViewHolder(View view) {
            super(view);
            iv_icon = view.findViewById(R.id.iv_icon);
            tv_icon_type = view.findViewById(R.id.tv_icon_type);
        }
    }

}
