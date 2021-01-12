package com.life.waimaishuo.mvvm.vm.order;

import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class ApplyAfterSalesViewModel extends BaseViewModel {

    private Order mOrder;

    @Override
    public BaseModel getModel() {
        return null;
    }

    @Override
    public void initData() {

    }

    public Order getOrder() {
        return mOrder;
    }

    public void setOrder(Order mOrder) {
        this.mOrder = mOrder;
    }
}
