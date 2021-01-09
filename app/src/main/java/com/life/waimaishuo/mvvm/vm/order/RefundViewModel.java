package com.life.waimaishuo.mvvm.vm.order;

import androidx.databinding.ObservableField;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.order.RefundModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class RefundViewModel extends BaseViewModel {

    RefundModel mModel;

    public ObservableField<String> refundReasonContentObservable = new ObservableField<>();

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new RefundModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }
}
