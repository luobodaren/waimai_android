package com.life.waimaishuo.mvvm.vm.order;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.vm.BaseRecyclerViewModel;

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
