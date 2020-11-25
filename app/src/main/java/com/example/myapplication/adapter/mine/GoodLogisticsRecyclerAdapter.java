package com.example.myapplication.adapter.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;

import java.util.List;

public class GoodLogisticsRecyclerAdapter extends BaseQuickAdapter<GoodLogisticsRecyclerAdapter.Data, GoodLogisticsRecyclerAdapter.ViewHolder> {


    public GoodLogisticsRecyclerAdapter(@Nullable List<Data> data) {
        super(R.layout.vertical_icon_data_show_item, data);
    }

    @Override
    protected void convert(@NonNull ViewHolder helper, Data item) {
        helper.iv_icon.setImageResource(item.iconId);
        helper.tv_icon_type.setText(item.iconType);
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

    public static class Data{
        int iconId;
        String iconType;

        public Data(@NonNull int iconId,@NonNull String iconType) {
            this.iconId = iconId;
            this.iconType = iconType;
        }

        public int getIconId() {
            return iconId;
        }

        public void setIconId(int iconId) {
            this.iconId = iconId;
        }

        public String getIconType() {
            return iconType;
        }

        public void setIconType(String iconType) {
            this.iconType = iconType;
        }
    }

}
