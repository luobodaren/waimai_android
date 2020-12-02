package com.example.myapplication.mvvm.vm.order;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.base.utils.LogUtil;
import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.adapter.MyBaseRecyclerAdapter;
import com.example.myapplication.bean.Goods;
import com.example.myapplication.bean.Order;
import com.example.myapplication.databinding.ItemGoodInfoBinding;
import com.example.myapplication.enumtype.OrderState;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;

import java.util.List;

public class OrderItemViewModel extends BaseRecyclerViewModel {

    Order order;

    @Override
    public void bindModel(BaseViewHolder mBaseViewHolder, BaseModel baseModel, MyBaseRecyclerAdapter adapter) {
        if(mBaseViewHolder.getItemViewType() == 0){


        }else if(mBaseViewHolder.getItemViewType() == 1){
            // TODO: 2020/11/30 根据不同type显示不同界面
            LogUtil.e("ViewHolder 匹配失败111111111111111111");
        }else{
            LogUtil.e("ViewHolder 匹配失败");
        }
    }

    @Override
    public BaseModel getModel() {
        return null;
    }

    @Override
    public void initData() {

    }

    public void setData(Order data) {
        this.order = data;
    }

    public Order getData() {
        return order;
    }

    public static class ViewHolder extends BaseViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
