package com.life.waimaishuo.mvvm.vm.order;

import androidx.databinding.ObservableField;

import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.order.RefundModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class RefundViewModel extends BaseViewModel {

    RefundModel mModel;

    public ObservableField<String> refundReasonContentObservable = new ObservableField<>();
    public ObservableField<String> goodsStateContentObservable = new ObservableField<>();

    private Order order;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new RefundModel();
        }
        return mModel;
    }

    @Override
    public void initData() {
        goodsStateContentObservable.set("货物状态");    //初始化界面显示文字
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
